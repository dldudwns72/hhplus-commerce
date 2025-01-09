package kr.hhplus.be.server.domain.order

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
@Table(name = "orders")
class OrderEntity(
    val userId: Long,
    val couponId: Long? = null,
    private var totalAmount: Long = 0L,
    var status: OrderStatus = OrderStatus.RESERVED, // enum 관리 필요

    // mappedBy = OrderProductEntity 에서 선언된 필드명
//    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
//    var orderProducts: List<OrderProductEntity> = mutableListOf()
) : BaseEntity() {

    fun successOrder(totalAmount: Long) {
        this.totalAmount = totalAmount
        status = OrderStatus.COMPLETED
    }

}

