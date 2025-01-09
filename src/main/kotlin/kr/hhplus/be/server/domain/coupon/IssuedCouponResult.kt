package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.controller.coupon.IssuedCouponResponse
import kr.hhplus.be.server.controller.user.dto.response.CouponUserResponse
import kr.hhplus.be.server.application.coupon.CouponUserResult

data class IssuedCouponResult(
    val name: String,
    val userId: Long,
    val couponId: Long,
    val discountType: CouponDiscountType,
    val discountValue: Int
)

fun CouponUserResult.toIssuedCouponResponse(
): IssuedCouponResponse {
    return IssuedCouponResponse(
        couponId = couponId,
        userId = userId,
        name = name,
        discountType = discountType,
        discountValue = discountValue
    )
}