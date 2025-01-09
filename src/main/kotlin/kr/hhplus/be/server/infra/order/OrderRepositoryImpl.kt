package kr.hhplus.be.server.infra.order

import kr.hhplus.be.server.domain.order.OrderEntity
import kr.hhplus.be.server.domain.order.OrderRepository
import kr.hhplus.be.server.domain.order.OrderStatus
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl(
    private val orderJpaRepository: OrderJpaRepository
) : OrderRepository {

    override fun findByUserId(userId: Long): List<OrderEntity> {
        return orderJpaRepository.findOrderByUserId(userId)
    }

    override fun saveOrder(orderEntity: OrderEntity): OrderEntity {
        orderJpaRepository.save(orderEntity)
        return orderEntity
    }

    override fun updateOrderStatus(id: Long, status: OrderStatus) {
        orderJpaRepository.updateOrderStatus(id, status)
    }

}