package kr.hhplus.be.server.controller.order.dto.response

import kr.hhplus.be.server.domain.order.OrderResult
import kr.hhplus.be.server.domain.order.OrderStatus

data class OrderResponse(
    val orderId: Long,
    val status: OrderStatus
)

fun OrderResult.toResponse() = OrderResponse(
    orderId = orderId,
    status = this.status
)