package kr.hhplus.be.server.domain.order

data class PaymentCompletedEvent(val orderId: Long, val userId: Long, val amount: Long)
