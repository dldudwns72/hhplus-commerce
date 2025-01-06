package kr.hhplus.be.server.domain.order

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
@Table(name = "orders")
class OrderEntity(
    val productId: Long,
    val userId: Long,
    val couponId: Long? = 0L,
    val totalAmount: Long,
    val status: String
) : BaseEntity() {
}

