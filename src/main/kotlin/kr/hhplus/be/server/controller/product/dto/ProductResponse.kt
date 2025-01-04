package kr.hhplus.be.server.controller.product.dto

data class ProductResponse(
    val id: Long,
    val title: String,
    val price: Int,
    val inventory: Int // 재고,
) {
}