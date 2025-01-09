package kr.hhplus.be.server.domain.order

data class OrderProductResult(
    val orderId: Long,
    val productId: Long,
    val productName: String,
    val productPrice: Long
) {
}

fun List<OrderProductEntity>.toResult(): List<OrderProductResult> {
    return this.map {
        OrderProductResult(
            orderId = it.order.id,
            productId = it.product.id,
            productName = it.product.name,
            productPrice = it.product.price
        )
    }
}
