package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.application.coupon.CouponFacade
import kr.hhplus.be.server.application.coupon.CouponService
import kr.hhplus.be.server.application.coupon.toCouponUserResult
import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import kr.hhplus.be.server.domain.order.OrderEntity
import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.domain.user.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CouponTest {

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var couponService: CouponService

    @Mock
    private lateinit var couponUserService: CouponUserService

    @InjectMocks
    private lateinit var couponFacade: CouponFacade

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
    @DisplayName("쿠폰 발급 시 COUPON_USER 생성")
    fun createCouponUser() {
        // given
        val mockUser = UserEntity(
            id = 1L,
            name = "LEE",
            balance = 100
        )
        val mockCoupon = CouponEntity(
            id = 1L,
            name = "COUPON",
            capacity = 100,
            discountType = CouponDiscountType.AMOUNT,
            discountValue = 1000
        )
        val mockCouponUser = CouponUserEntity(id = 1L, mockUser, mockCoupon)

        // when
        `when`(userService.getUserById(1L)).thenReturn(mockUser)
        `when`(couponService.getCoupon(1L)).thenReturn(mockCoupon)
        `when`(couponUserService.create(mockUser, mockCoupon)).thenReturn(mockCouponUser)

        couponFacade.issue(1L, 1L)

        // then
        // verify(couponUserService.create(mockUser, mockCoupon)) // 이거 안됨..?
        verify(couponUserService).create(mockUser, mockCoupon)
    }


    @Test
    @DisplayName("쿠폰 사용 시 유저 쿠폰 상태 업데이트(사용중)")
    fun couponUse() {
        //given
        val coupon = CouponEntity(
            name = "쿠폰1",
            capacity = 0,
            discountType = CouponDiscountType.AMOUNT,
            discountValue = 1000
        )
        val user = UserEntity(name = "유저1", balance = 100)
        val couponUserEntity = CouponUserEntity(
            user = user,
            coupon = coupon
        )
        // when
        couponUserEntity.use()

        // then
        Assertions.assertEquals(couponUserEntity.isUsed, true)
    }

    @Test
    @DisplayName("사용된 쿠폰 사용 시 예외 발생")
    fun usedCoupon() {
        //given
        val coupon = CouponEntity(
            name = "쿠폰1",
            capacity = 0,
            discountType = CouponDiscountType.AMOUNT,
            discountValue = 1000
        )
        val user = UserEntity(name = "유저1", balance = 100)
        val couponUserEntity = CouponUserEntity(
            user = user,
            coupon = coupon
        )

        // then
        assertThrows<IllegalArgumentException> {
            // when
            couponUserEntity.use()
        }
    }


}