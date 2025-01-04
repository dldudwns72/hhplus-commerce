package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.common.BaseEntity

class OrderEntity(
    val productId: Long,
    val userId: Long,
    val couponId: Long? = 0L,
    val totalAmount: Long,
    val status: String
) : BaseEntity() {
}

