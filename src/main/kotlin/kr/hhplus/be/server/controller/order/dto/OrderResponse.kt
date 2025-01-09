package kr.hhplus.be.server.controller.order.dto

import kr.hhplus.be.server.domain.order.OrderStatus

data class OrderResponse(
    val orderId: Long,
    val totalAmount: Long,
    val status: OrderStatus
)