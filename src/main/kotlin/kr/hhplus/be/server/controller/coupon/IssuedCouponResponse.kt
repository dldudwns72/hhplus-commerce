package kr.hhplus.be.server.controller.coupon

import kr.hhplus.be.server.domain.coupon.CouponDiscountType

data class IssuedCouponResponse(
    val name: String,
    val userId: Long,
    val couponId: Long,
    val discountType: CouponDiscountType,
    val discountValue: Int
)
