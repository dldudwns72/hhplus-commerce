package kr.hhplus.be.server.controller.coupon.dto

import java.math.BigDecimal

data class CouponResponse(
    val id: Long,
    val name: String,
    val balance: BigDecimal
) {
}