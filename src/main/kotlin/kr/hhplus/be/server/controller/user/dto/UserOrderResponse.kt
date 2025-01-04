package kr.hhplus.be.server.controller.user.dto

import java.math.BigDecimal

data class UserOrderResponse(
    val userId: Long,
    val orderId: Long
) {
}