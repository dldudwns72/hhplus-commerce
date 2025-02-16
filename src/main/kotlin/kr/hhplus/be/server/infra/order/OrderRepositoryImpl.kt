package kr.hhplus.be.server.infra.order

import kr.hhplus.be.server.domain.order.OrderEntity
import kr.hhplus.be.server.domain.order.OrderRepository
import kr.hhplus.be.server.domain.order.OrderStatus
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class OrderRepositoryImpl(
    private val orderJpaRepository: OrderJpaRepository
) : OrderRepository {

    override fun findByUserId(userId: Long): List<OrderEntity> {
        return orderJpaRepository.findOrderByUserId(userId)
    }

    override fun findById(id: Long): OrderEntity? {
        return orderJpaRepository.findById(id).getOrNull()
    }

    override fun saveOrder(orderEntity: OrderEntity): OrderEntity {
        return orderJpaRepository.save(orderEntity)
    }

    override fun updateOrderStatus(id: Long, status: OrderStatus) {
        orderJpaRepository.updateOrderStatus(id, status)
    }

}