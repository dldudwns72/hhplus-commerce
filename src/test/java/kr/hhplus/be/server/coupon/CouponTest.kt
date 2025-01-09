package kr.hhplus.be.server.coupon

import kr.hhplus.be.server.application.coupon.CouponService
import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.coupon.CouponRepository
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CouponTest {

    @InjectMocks
    private lateinit var couponService: CouponService

    @Mock
    private lateinit var couponRepository: CouponRepository


    @Test
    @DisplayName("쿠폰이 발행되면 재고 쿠폰 수가 1 감소")
    fun deductedCouponCapacity() {
        // given
        val mockCoupon = CouponEntity(
            capacity = 100
        )
        // when
        val couponId = 1L
        val userId = 1L
        `when`(couponRepository.findById(couponId)).thenReturn(mockCoupon)
        couponService.issue(couponId, userId)

        // then
        Assertions.assertEquals(mockCoupon.capacity, 99)
    }

    @Test
    @DisplayName("쿠폰 사용량이 0개일 때 쿠폰 발행 시 예외 발생")
    fun deductedCouponOutOfCapacity() {
        // given
        val mockCoupon = CouponEntity(
            capacity = 0
        )
        // when
        val couponId = 1L
        val userId = 1L
        `when`(couponRepository.findById(couponId)).thenReturn(mockCoupon)

        // then
        assertThrows<IllegalArgumentException> {
            couponService.issue(couponId, userId)
        }
    }

    @Test
    @DisplayName("주문 시 쿠폰 사용")
    fun useCoupon() {
        // given
        val userId = 1L
        val couponId = 1L
        val orderId = 1L
        val userCoupon = CouponUserEntity(
            userId = userId,
            couponId = couponId,
            orderId = orderId
        )
        // when
        userCoupon.use(orderId)

        // then
        Assertions.assertEquals(userCoupon.isUsed, true)
        Assertions.assertEquals(userCoupon.orderId, 1)
    }
}