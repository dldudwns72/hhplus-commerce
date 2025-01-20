package kr.hhplus.be.server.controller.order.dto.request

import kr.hhplus.be.server.controller.order.dto.OrderProductRequest

data class OrderRequest(
    val orderProducts: List<OrderProductRequest>,
    val couponUserId: Long? = null
)