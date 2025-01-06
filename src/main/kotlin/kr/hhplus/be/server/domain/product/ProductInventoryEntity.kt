package kr.hhplus.be.server.domain.product

import jakarta.persistence.*

@Entity
@Table(name = "product_inventory")
open class ProductInventoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    val productId: Long
) {

}