package kr.hhplus.be.server.controller.order.dto

data class OrderRequest(
    val productId: Long,
    val quantity: Int,
    val couponId: Long? = 0L
)