package kr.hhplus.be.server.domain.product

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
@Table(name = "product_inventory")
class ProductInventoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    val productId: Long,
    var inventory: Int
) {

    fun decreaseInventory() {
        if (inventory == 0) throw IllegalArgumentException("상품 재고가 존재하지 않습니다.")
        inventory - 1
    }
}