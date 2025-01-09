package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.user.UserService
import kr.hhplus.be.server.controller.coupon.IssuedCouponResponse
import kr.hhplus.be.server.domain.coupon.CouponUserService
import kr.hhplus.be.server.domain.coupon.toIssuedCouponResponse
import org.springframework.stereotype.Service

/**
 * 유즈케이스 -> 문제를 해결하기 위한 요구사항
 * 파사드 -> 여러 객체간의 협력을 위한 디자인패턴 // 파사드는 인터페이스로 나타낸다.?
 */
@Service
class CouponFacade(
    private val couponService: CouponService,
    private val userService: UserService,
    private val couponUserService: CouponUserService
) {
    fun issue(couponId: Long, userId: Long): CouponUserResult {
        val user = userService.getUserById(userId)
        val coupon  = couponService.getCoupon(couponId)
        coupon.issue()
        return couponUserService.create(user, coupon)
    }
}