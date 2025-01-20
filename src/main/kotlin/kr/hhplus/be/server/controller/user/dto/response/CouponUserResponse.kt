package kr.hhplus.be.server.controller.user.dto.response

import kr.hhplus.be.server.application.coupon.CouponUserResult
import kr.hhplus.be.server.domain.coupon.CouponDiscountType
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import java.time.LocalDateTime


data class CouponUserResponse(
    val couponId: Long,
    val name: String,
    val discountType: CouponDiscountType,
    val discountValue: Int,
    val isUsed: Boolean,
    val issuedAt: LocalDateTime,
) {
}

fun CouponUserResult.toResponse() = CouponUserResponse(
    couponId = id,
    name = name,
    discountType = discountType,
    discountValue = discountValue,
    isUsed = isUsed,
    issuedAt = issuedAt,
)