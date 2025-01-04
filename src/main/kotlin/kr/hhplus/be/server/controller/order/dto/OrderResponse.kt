package kr.hhplus.be.server.controller.order.dto

data class OrderResponse(
    val orderId: Long,
    val totalAmount: Double,
    val status: String
)