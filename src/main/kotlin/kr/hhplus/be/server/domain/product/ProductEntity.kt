package kr.hhplus.be.server.domain.product

import kr.hhplus.be.server.domain.common.BaseEntity

class ProductEntity(
    val name: String,
    val price: String,
) : BaseEntity() {
}