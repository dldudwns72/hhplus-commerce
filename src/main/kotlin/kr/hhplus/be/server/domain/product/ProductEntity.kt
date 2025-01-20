package kr.hhplus.be.server.domain.product

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import kr.hhplus.be.server.domain.order.OrderProductEntity

@Entity
@Table(name = "product")
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    val name: String,
    val price: Long,
//    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    var orderProducts: MutableList<OrderProductEntity> = mutableListOf(),
    @OneToOne(mappedBy = "product", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var productInventory: ProductInventoryEntity
) : BaseEntity() {

//    init {
//        productInventory.product = this // 양방향 관계 설정
//    }
}