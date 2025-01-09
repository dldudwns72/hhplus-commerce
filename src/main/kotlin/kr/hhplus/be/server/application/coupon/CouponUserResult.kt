package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponDiscountType
import kr.hhplus.be.server.domain.coupon.CouponUserEntity

data class CouponUserResult(
    val id: Long,
    val userId: Long,
    val couponId: Long,
    val name: String,
    val discountType: CouponDiscountType,
    val discountValue: Int,
    val isUsed: Boolean
)

fun CouponUserEntity.toCouponUserResult() = CouponUserResult(
    id = this.id,
    userId = this.user.id,
    couponId = this.coupon.id,
    name = this.coupon.name,
    discountType = this.coupon.discountType,
    discountValue = this.coupon.discountValue,
    isUsed = this.isUsed
)
