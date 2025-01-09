package kr.hhplus.be.server.infra.order

import kr.hhplus.be.server.domain.order.OrderProductEntity
import kr.hhplus.be.server.domain.order.OrderProductRepository
import kr.hhplus.be.server.domain.order.OrderProductResult
import org.springframework.stereotype.Repository

@Repository
class OrderProductRepositoryImpl(
    private val orderProductJpaRepository: OrderProductJpaRepository
) : OrderProductRepository {
    override fun saveAll(orderProducts: List<OrderProductEntity>) {
        orderProductJpaRepository.saveAll(orderProducts)
    }

    override fun findByUserId(userId: Long): List<OrderProductResult> {
        return orderProductJpaRepository.findOrderProductEntityByUserId(userId)
    }
}