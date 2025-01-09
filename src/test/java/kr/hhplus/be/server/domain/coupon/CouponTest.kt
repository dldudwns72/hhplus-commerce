package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import kr.hhplus.be.server.domain.order.OrderEntity
import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.user.UserEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CouponTest {

    @Test
    @DisplayName("쿠폰이 발행되면 재고 쿠폰 수가 1 감소")
    fun deductedCouponCapacity() {
        // given
        val coupon = CouponEntity(
            name = "쿠폰1",
            capacity = 100,
            discountType = CouponDiscountType.AMOUNT,
            discountValue = 1000
        )

        // when
        coupon.issue()

        // then
        Assertions.assertEquals(coupon.name, "쿠폰1")
        Assertions.assertEquals(coupon.capacity, 99)
    }

    @Test
    @DisplayName("쿠폰 사용량이 0개일 때 쿠폰 발행 시 예외 발생")
    fun deductedCouponOutOfCapacity() {
        // given
        val coupon = CouponEntity(
            name = "쿠폰1",
            capacity = 0,
            discountType = CouponDiscountType.AMOUNT,
            discountValue = 1000
        )

        // then
        assertThrows<IllegalArgumentException> {
            // when
            coupon.issue()
        }
    }

    @Test
    @DisplayName("쿠폰 사용 시 유저 쿠폰 상태 업데이트")
    fun couponUse() {
        //given
        val coupon = CouponEntity(
            name = "쿠폰1",
            capacity = 0,
            discountType = CouponDiscountType.AMOUNT,
            discountValue = 1000
        )
        val user = UserEntity(name = "유저1", balance = 100)
        val order = OrderEntity(user = user, totalAmount = 200L) // total_amount..?
        val couponUserEntity = CouponUserEntity(
            user = user,
            coupon = coupon,
            order = order
        )
        // when
        couponUserEntity.use(order)

        // then
        Assertions.assertEquals(couponUserEntity.isUsed, true)
    }


}