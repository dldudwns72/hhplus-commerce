package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.domain.order.OrderProductResult

data class OrderResult(
    val orderId: Long,
    val orderProducts: List<OrderProductResult>
) {

}