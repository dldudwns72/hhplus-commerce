package kr.hhplus.be.server.controller.product.dto

data class PopularProductResponse(
    val id: Long,
    val title: String,
    val price: Int,
    val inventory: Int,
    val rank: Int,
    val orderCount: Int
) {
}