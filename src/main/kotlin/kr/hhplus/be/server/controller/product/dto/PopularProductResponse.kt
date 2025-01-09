package kr.hhplus.be.server.controller.product.dto

import kr.hhplus.be.server.domain.product.PopularProductResult

data class PopularProductResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val inventory: Int,
    val rank: Int,
    val orderCount: Long
) {
}

fun PopularProductResult.toPopularProductResponse() = PopularProductResponse(
    id = id,
    name = name,
    price = price,
    inventory = inventory,
    rank = 1,
    orderCount = orderCount
)