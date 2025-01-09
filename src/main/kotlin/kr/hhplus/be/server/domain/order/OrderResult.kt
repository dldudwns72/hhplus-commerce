package kr.hhplus.be.server.domain.order

data class OrderResult(
    val orderId: Long,
    val status: OrderStatus
) {
}

fun OrderEntity.toResult() = OrderResult(
    orderId = this.id,
    status = this.status
)