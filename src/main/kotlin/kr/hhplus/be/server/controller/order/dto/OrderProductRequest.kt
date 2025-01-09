package kr.hhplus.be.server.controller.order.dto

data class OrderProductRequest(
    val productId: Long,
    val quantity: Int,
) {
}