package kr.hhplus.be.server.integration.kafka

import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.testcontainers.shaded.org.awaitility.Awaitility
import java.util.concurrent.TimeUnit

@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, topics = ["topic","test-consumer-topic"])
class KafkaTest {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>  // 필드 주입


    private var consumerTestFlag = false  // Consumer가 메시지를 받았는지 확인하는 플래그

    // KafkaListener로 메시지를 소비하는 메소드
    @KafkaListener(topics = ["test-consumer-topic"], groupId = "consumer-group")
    fun listen(message: String) {
        if (message == "test-consumer-message") {
            consumerTestFlag = true  // 메시지를 받으면 플래그를 true로 설정
        }
    }
    @BeforeEach
    fun setUp() {
        consumerTestFlag = false
    }

    @Test
    @DisplayName("Kafka Producer 발행 및 Consumer 메시지 소비 테스트")
    fun kafkaTest() {
        // 메시지 발행
        kafkaTemplate.send(ProducerRecord("test-consumer-topic", "test-key", "test-consumer-message")).get()

        // Awaitility를 사용하여 비동기 처리 대기
        Awaitility.await().atMost(5, TimeUnit.SECONDS).until { consumerTestFlag }

        // test가 true로 업데이트 되었는지 확인
        assertTrue(consumerTestFlag, "Consumer가 메시지를 소비하지 못했습니다.")
    }
}
