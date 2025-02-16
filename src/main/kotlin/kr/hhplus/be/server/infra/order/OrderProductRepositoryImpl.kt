package kr.hhplus.be.server.infra.order

import kr.hhplus.be.server.domain.order.OrderProductEntity
import kr.hhplus.be.server.domain.order.OrderProductRepository
import org.springframework.stereotype.Repository

@Repository
class OrderProductRepositoryImpl(
    private val orderProductJpaRepository: OrderProductJpaRepository
) : OrderProductRepository {
    override fun saveAll(orderProducts: List<OrderProductEntity>): List<OrderProductEntity> {
        return orderProductJpaRepository.saveAll(orderProducts)
    }

    override fun findAllByOrderId(orderId: Long): List<OrderProductEntity> {
        return orderProductJpaRepository.findAllByOrderId(orderId)
    }
}