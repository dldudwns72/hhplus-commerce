package kr.hhplus.be.server.config.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(

    @Value("\${spring.data.redis.host}")
    private val host: String,
    @Value("\${spring.data.redis.port}")
    private val port: Int,
    @Value("\${spring.data.redis.password}")
    private val password: String?
) {


    // Lettuce Connection 을 이용한 Redis 연결
    // Spring-Data-Redis
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = host
        redisStandaloneConfiguration.port = port
        // redisStandaloneConfiguration.password = RedisPassword.of(password)
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.connectionFactory = factory // RedisTemplate 이 Redis 서버와 통신하기 위한 연결 정보 주입
        // Redis는 데이터를 바이트(Byte) 배열로 저장하기 때문에 직렬화 설정이 필요해
        template.keySerializer = StringRedisSerializer() // StringRedisSerializer()를 사용하면 String을 그대로 저장 & 읽기 가능
        template.valueSerializer = StringRedisSerializer() // Json 직렬화를 위해선 Jackson2JsonRedisSerializer 같은거 사용 가능
        return template
    }

}