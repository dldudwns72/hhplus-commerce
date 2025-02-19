package kr.hhplus.be.server

import jakarta.annotation.PreDestroy
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.kafka.ConfluentKafkaContainer
import org.testcontainers.utility.DockerImageName

@Configuration
class TestcontainersConfiguration {
    @PreDestroy
    fun preDestroy() {
        if (mySqlContainer.isRunning) mySqlContainer.stop()
        if (redisContainer.isRunning) redisContainer.stop()
        if (kafkaContainer.isRunning) kafkaContainer.stop()
    }

    companion object {
        val mySqlContainer: MySQLContainer<*> = MySQLContainer(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("e-commerce")
            .withUsername("hhplus")
            .withPassword("hhplus")
            // .withInitScript("db/hhplus/init.sql")
            .apply {
                start()
            }

        @ServiceConnection
        val redisContainer: GenericContainer<*> =
            GenericContainer<Nothing>(DockerImageName.parse("redis:7.4.2"))
                .apply {
                    withExposedPorts(6379)
                    start()
                }

        val kafkaContainer: ConfluentKafkaContainer =
            ConfluentKafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.8.1"))
                .apply {
                    start()
                }

        init {
            System.setProperty("spring.datasource.url", mySqlContainer.getJdbcUrl() + "?characterEncoding=UTF-8&serverTimezone=UTC")
            System.setProperty("spring.datasource.username", mySqlContainer.username)
            System.setProperty("spring.datasource.password", mySqlContainer.password)

            System.setProperty("spring.kafka.bootstrap-servers", kafkaContainer.bootstrapServers)
        }
    }
}
