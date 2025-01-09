package kr.hhplus.be.server.controller.product.dto

import kr.hhplus.be.server.domain.product.ProductResult
import kr.hhplus.be.server.infra.product.ProductQuery

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val inventory: Int // 재고,
) {
}

fun ProductResult.toProductResponse () =
    ProductResponse(
        id = id,
        name = name,
        price = price,
        inventory = inventory
    )