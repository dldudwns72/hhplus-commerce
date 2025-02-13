package kr.hhplus.be.server.integration.usecase.coupon

import jakarta.transaction.Transactional
import kr.hhplus.be.server.application.coupon.CouponFacade
import kr.hhplus.be.server.domain.coupon.CouponDiscountType
import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.infra.coupon.CouponJpaRepository
import kr.hhplus.be.server.infra.coupon.CouponUserJpaRepository
import kr.hhplus.be.server.infra.user.UserJpaRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger
import kotlin.jvm.optionals.getOrNull
import kotlin.test.assertEquals

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CouponIssueTest {

    @Autowired
    private lateinit var couponFacade: CouponFacade

    @Autowired
    private lateinit var couponJpaRepository: CouponJpaRepository

    @Autowired
    private lateinit var couponUserJpaRepository: CouponUserJpaRepository

    @Autowired
    private lateinit var userJpaRepository: UserJpaRepository

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>

    private lateinit var coupon: CouponEntity
    private lateinit var user: UserEntity

    @BeforeEach
    fun init() {
        couponUserJpaRepository.deleteAllInBatch()
        couponJpaRepository.deleteAllInBatch()
        userJpaRepository.deleteAllInBatch()
        coupon = couponJpaRepository.saveAndFlush(
            CouponEntity(
                name = "COUPON1",
                capacity = 100,
                maxCapacity = 100,
                discountValue = 10,
                discountType = CouponDiscountType.AMOUNT
            )
        )
        user = userJpaRepository.saveAndFlush(UserEntity(name = "testUser", balance = 0))
        redisTemplate.delete("coupon:${coupon.id}")
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

    @Test
    @Transactional
    @DisplayName("[성공] 쿠폰 발행 성공(단건)")
    fun issueCoupon() {
        couponFacade.issue(coupon.id, user.id)
        val coupon: CouponEntity? = couponJpaRepository.findById(coupon.id).getOrNull()
        Assertions.assertTrue(coupon?.capacity == 99)
    }

    @Test
    @Transactional
    @DisplayName("[성공] 쿠폰 발행 성공(수량 만큼)")
    fun issueAllCoupon() {
        val coupon: CouponEntity = couponJpaRepository.findById(coupon.id).orElseThrow()

        val savedUsers = userJpaRepository.saveAllAndFlush((1..coupon.maxCapacity).map {
            UserEntity(name = "testUser:$it", balance = 0)
        })

        savedUsers.forEach { user ->
            couponFacade.issue(coupon.id, user.id)
        }
        Assertions.assertTrue(coupon?.capacity == 0)
    }


    @Test
    @DisplayName("[예외] 이미 지급된 쿠폰일 경우 예외 발생")
    fun alreadyIssuedCoupon() {
        couponFacade.issue(coupon.id, user.id)
        val exception = assertThrows<IllegalStateException> {
            couponFacade.issue(coupon.id, user.id)
        }
        assertEquals("해당 쿠폰을 이미 발급 받았습니다.", exception.message)
    }

    @Test
    @DisplayName("[예외] 쿠폰 수량이 소진되었을 때 예외 발생")
    fun soldOutCoupon() {
        val savedUsers = userJpaRepository.saveAllAndFlush((1..100L).map {
            UserEntity(name = "testUser:$it", balance = 0)
        })

        savedUsers.forEach { user ->
            couponFacade.issue(coupon.id, user.id)
        }

        val lastUser = userJpaRepository.saveAndFlush(UserEntity(name = "testUser", balance = 99))
        val exception = assertThrows<IllegalStateException> {
            couponFacade.issue(coupon.id, lastUser.id)
        }
        assertEquals("쿠폰 수량이 소진 되었습니다.", exception.message)
    }


}
