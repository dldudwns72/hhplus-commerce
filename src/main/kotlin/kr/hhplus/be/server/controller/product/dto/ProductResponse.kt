package kr.hhplus.be.server.controller.product.dto

import kr.hhplus.be.server.domain.product.ProductEntity

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val inventory: Int // 재고,
) {
}

fun ProductEntity.toProductResponse() =
    ProductResponse(
        id = id,
        name = name,
        price = price,
        inventory = productInventory?.inventory ?: 0
    )