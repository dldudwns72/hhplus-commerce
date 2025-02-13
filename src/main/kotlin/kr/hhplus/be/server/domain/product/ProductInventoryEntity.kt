package kr.hhplus.be.server.domain.product

import jakarta.persistence.*
import kr.hhplus.be.server.controller.common.ProductException
import kr.hhplus.be.server.domain.common.BaseEntity
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "product_inventory")
class ProductInventoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    var inventory: Int,
    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_id", nullable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var product: ProductEntity? = null,
    @Version
    @ColumnDefault("0")
    var version: Int = 0
) {

    fun decreaseInventoryCount() {
        if (inventory == 0) throw ProductException("상품 재고가 존재하지 않습니다.")
        inventory - 1
    }
}