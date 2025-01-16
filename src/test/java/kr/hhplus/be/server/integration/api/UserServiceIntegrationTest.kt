package kr.hhplus.be.server.integration.api

import kr.hhplus.be.server.TestcontainersConfiguration
import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.domain.user.UserRepository
import kr.hhplus.be.server.domain.user.UserService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.MySQLContainer

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceIntegrationTest(
    @Autowired private val userService: UserService,
    @Autowired private val userRepository: UserRepository
) {

    companion object {
        @JvmStatic
        private val mySqlContainer: MySQLContainer<*> = TestcontainersConfiguration.mySqlContainer
    }

    @BeforeEach
    fun setUp() {
        // 테스트 데이터 초기화
        userRepository.save(UserEntity(name = "LEE", balance = 100L))
    }

    @Test
    fun `유저의 포인트를 충전한다`() {
        // Given
        val userId = 1L
        val chargePoint = 50L

        // When
        val response = userService.chargePoint(userId, chargePoint)

        // Then
        Assertions.assertEquals(response.balance, 150L)
    }

    @Test
    fun `유저의 포인트를 사용한다`() {
        // Given
        val userId = 1L
        val usePoint = 30L

        // When
        val response = userService.usePoint(userId, usePoint)

        // Then
        Assertions.assertEquals(response.balance, 70L)
    }

    @Test
    fun `유저의 잔액을 조회한다`() {
        // Given
        val userId = 1L

        // When
        val user = userService.getUserById(userId)

        // Then
        Assertions.assertEquals(user.balance, 100L)

    }

    @Test
    fun `존재하지 않는 유저를 조회하면 예외를 던진다`() {
        // Given
        val nonExistentUserId = 999L

        // When & Then
        assertThrows<IllegalArgumentException> {
            userService.getUserById(nonExistentUserId)
        }.apply {
            Assertions.assertEquals(message, "유저를 찾을 수 없습니다.")
        }
    }
}