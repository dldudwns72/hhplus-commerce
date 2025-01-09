package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.controller.user.dto.UserCouponResponse

data class IssuedCouponResult(
    val couponId: Long,
    val userId: Long,
    val name: String,
    val discountType: CouponDiscountType,
    val discountValue: Long
) {
}

fun IssuedCouponResult.toUserCouponResponse() = UserCouponResponse(
    couponId = couponId,
    name = name,
    discountType = discountType,
    discountValue = discountValue
)