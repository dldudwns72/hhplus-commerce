package kr.hhplus.be.server.domain.order.event

import kr.hhplus.be.server.domain.order.OrderEntity

data class OrderCompletedEvent(val order: OrderEntity) {
}