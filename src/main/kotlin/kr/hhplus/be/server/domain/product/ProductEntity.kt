package kr.hhplus.be.server.domain.product

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
@Table(name = "product")
class ProductEntity(
    val name: String,
    val price: String,
) : BaseEntity() {
}