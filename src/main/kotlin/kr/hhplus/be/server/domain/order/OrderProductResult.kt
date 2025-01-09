package kr.hhplus.be.server.domain.order

data class OrderProductResult(
    val orderId: Long,
    val productId: Long,
    val productName: String,
    val productPrice: Long
) {
}