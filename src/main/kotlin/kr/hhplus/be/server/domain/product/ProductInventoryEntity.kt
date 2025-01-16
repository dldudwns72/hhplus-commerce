package kr.hhplus.be.server.domain.product

import jakarta.persistence.*
import kr.hhplus.be.server.controller.common.exception.ProductException

@Entity
@Table(name = "product_inventory")
class ProductInventoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    var inventory: Int,
    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_id", nullable = false)
    var product: ProductEntity? = null
) {

    fun decreaseInventoryCount() {
        if (inventory == 0) throw ProductException("상품 재고가 존재하지 않습니다.")
        inventory - 1
    }
}