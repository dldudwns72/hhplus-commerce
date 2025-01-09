package kr.hhplus.be.server.controller.user.dto

import kr.hhplus.be.server.domain.order.OrderProductEntity


data class UserOrderResponse(
    val productId: Long,
    val orderId: Long,
    val name: String,
    val quantity: Int,
    val price: Long
)

fun OrderProductEntity.toUserOrderResponse(): UserOrderResponse {
    return UserOrderResponse(
        productId = productId,
        orderId = orderId,
        quantity = count,
        name = "상품명",
        price = 10000
    )
}



