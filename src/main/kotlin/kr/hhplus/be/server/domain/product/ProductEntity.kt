package kr.hhplus.be.server.domain.product

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import kr.hhplus.be.server.domain.order.OrderProductEntity

@Entity
@Table(name = "product")
class ProductEntity(
    val name: String,
    val price: Long,
    @OneToOne(mappedBy = "product", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var productInventory: ProductInventoryEntity
) : BaseEntity() {

    init {
        productInventory.product = this // 양방향 관계 설정
    }
}