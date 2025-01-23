package kr.hhplus.be.server.integration.usecase.coupon

import kr.hhplus.be.server.application.coupon.CouponFacade
import kr.hhplus.be.server.domain.coupon.CouponDiscountType
import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.domain.user.UserRepository
import kr.hhplus.be.server.infra.coupon.CouponJpaRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CouponIssueTest {

    @Autowired
    private lateinit var couponFacade: CouponFacade


    @Autowired
    private lateinit var couponJpaRepository: CouponJpaRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    private lateinit var coupon: CouponEntity
    private lateinit var user: UserEntity

    @BeforeEach
    fun init() {
        val couponId = 0L
        val userId = 0L
        coupon = couponJpaRepository.saveAndFlush(
            CouponEntity(
                id = couponId,
                name = "COUPON1",
                capacity = 1,
                discountValue = 10,
                discountType = CouponDiscountType.AMOUNT
            )
        )
        user = userRepository.saveAndFlush(UserEntity(id = userId, name = "testUser", 0))

    }

    @Test
    fun `쿠폰 발행 비관락 테스트`() {
        // given

        val threadCount = 10 // 동시 실행할 스레드 수
        val successCount = AtomicInteger(0) // 성공 횟수
        val failCount = AtomicInteger(0)    // 실패 횟수

        // when
        val futures = (1..threadCount).map {
            CompletableFuture.runAsync {
                try {
                    couponFacade.issue(coupon.id, user.id) // 쿠폰 발행 호출
                    successCount.incrementAndGet()
                } catch (e: Exception) {
                    failCount.incrementAndGet()
                }
            }
        }

        // 모든 작업이 완료될 때까지 대기
        CompletableFuture.allOf(*futures.toTypedArray()).join()

        // 성공 횟수는 쿠폰 재고 수를 초과하지 않아야 함
        Assertions.assertEquals(1, successCount.get())
        // 실패 횟수는 threadCount - 1이어야 함
        Assertions.assertEquals(threadCount - 1, failCount.get())
    }
}
