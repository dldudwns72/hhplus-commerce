package kr.hhplus.be.server.domain.order.event

data class OrderCanceledEvent(val orderId: Long, val userId: Long, val amount: Long) {
}