package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponDiscountType
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import java.time.LocalDateTime

data class CouponUserResult(
    val id: Long,
    val couponId: Long,
    val name: String,
    val discountType: CouponDiscountType,
    val discountValue: Int,
    val isUsed: Boolean,
    val issuedAt: LocalDateTime,
)

fun CouponUserEntity.toCouponUserResult() = CouponUserResult(
    id = this.id,
    couponId = this.coupon.id,
    name = this.coupon.name,
    discountType = this.coupon.discountType,
    discountValue = this.coupon.discountValue,
    isUsed = this.isUsed,
    issuedAt = this.createdAt
)
