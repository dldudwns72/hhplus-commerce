package kr.hhplus.be.server.domain.product

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
@Table(name = "product")
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    val name: String,
    val price: Long,
    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var productInventory: ProductInventoryEntity
) : BaseEntity() {
}