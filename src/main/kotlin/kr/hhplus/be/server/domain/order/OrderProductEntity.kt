package kr.hhplus.be.server.domain.order

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import kr.hhplus.be.server.domain.product.ProductEntity

@Entity
@Table(name = "order_product")
class OrderProductEntity(

    @Column(name = "count", nullable = false)
    val count: Int,

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id", nullable = false)
//    var order: OrderEntity,

    @Column(name = "order_id", nullable = false)
    var orderId: Long = 0,

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
//    var product: ProductEntity,

    @Column(name = "product_id", nullable = false)
    var productId: Long = 0,
) : BaseEntity() {


}