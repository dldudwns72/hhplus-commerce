package kr.hhplus.be.server.domain.order

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import kr.hhplus.be.server.domain.product.ProductEntity

@Entity
@Table(name = "order_product")
class OrderProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    // 2개 row 생성?
    @Column(name = "quantity", nullable = false)
    val quantity: Int,

    // 1개의 주문에 order_product 가 N개 생성될 수 있으니깐 1 order: N order_product -> order_product:Many order:One => ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    var order: OrderEntity,

    // 1개의 상품에 order_product 가 N개 생성될 수 있으니깐 1 product: N order_product -> order_product:Many product:One => ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    var product: ProductEntity
) : BaseEntity() {


}

