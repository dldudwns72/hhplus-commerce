package kr.hhplus.be.server.controller.user.dto

import kr.hhplus.be.server.domain.coupon.CouponDiscountType


data class UserCouponResponse(
    val couponId: Long,
    val name: String,
    val discountType: CouponDiscountType,
    val discountValue: Long
) {
}

