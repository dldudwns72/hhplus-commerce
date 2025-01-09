package kr.hhplus.be.server.domain.product

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import kr.hhplus.be.server.domain.order.OrderProductEntity

@Entity
@Table(name = "product")
class ProductEntity(
    val name: String,
    val price: Int,

//    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
//    var orderProducts: List<OrderProductEntity> = mutableListOf()
) : BaseEntity() {

}