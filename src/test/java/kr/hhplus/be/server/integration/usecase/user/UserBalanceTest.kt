package kr.hhplus.be.server.integration.usecase.user

import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.domain.user.UserRepository
import kr.hhplus.be.server.domain.user.UserService
import kr.hhplus.be.server.infra.user.UserJpaRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.ObjectOptimisticLockingFailureException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserBalanceTest {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userJpaRepository: UserJpaRepository


    private lateinit var user: UserEntity

    @BeforeEach
    fun init() {
        val userId = 0L
        user = userRepository.saveAndFlush(UserEntity(id = userId, name = "testUser", 0))
    }

    @AfterEach
    fun cleanup() {
        // @Transactional 을 사용할 수 없다.
        // entityManager.createNativeQuery("DELETE FROM UserPoint WHERE user_id = ${user.id}").executeUpdate()
        // domainRepository 에서 테스트만을 위한 메서드를 사용하는건 좋지 않다.

        userJpaRepository.flush()
        userJpaRepository.deleteAll()
    }

    @Test
    fun testGetUserPoint() {
        val threadCount = 10

        val successCount = AtomicInteger(0) // 성공 횟수
        val failCount = AtomicInteger(0)    // 실패 횟수

        // CompletableFuture 리스트 생성
        val futures = mutableListOf<CompletableFuture<Void>>()

        for (i in 1..threadCount) {
            // CompletableFuture.runAsync 를 이용해 비동기 작업 수행
            val future = CompletableFuture.runAsync {
                try {
                    userService.chargePoint(user.id, 1000)
                    successCount.incrementAndGet()
                } catch (e: ObjectOptimisticLockingFailureException) {
                    failCount.incrementAndGet()
                }
            }
            futures.add(future)
        }

        // 모든 작업 완료 대기
        CompletableFuture.allOf(*futures.toTypedArray()).join()

        Assertions.assertTrue(successCount.get() < 10)
        Assertions.assertTrue(failCount.get() > 0)

        val updatedUser = userRepository.findById(user.id)
        Assertions.assertEquals(updatedUser?.balance, 1000L * successCount.get())
    }
}