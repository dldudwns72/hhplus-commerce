package kr.hhplus.be.server.controller.user.dto.response

import kr.hhplus.be.server.domain.coupon.CouponDiscountType
import kr.hhplus.be.server.domain.coupon.CouponUserEntity


data class CouponUserResponse(
    val couponId: Long,
    val name: String,
    val discountType: CouponDiscountType,
    val discountValue: Int
) {
}

fun CouponUserEntity.toResponse() = CouponUserResponse(
    couponId = id,
    name = coupon.name,
    discountType = coupon.discountType,
    discountValue = coupon.discountValue,
)