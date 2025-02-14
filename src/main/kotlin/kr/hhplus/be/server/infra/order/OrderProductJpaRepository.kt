package kr.hhplus.be.server.infra.order

import kr.hhplus.be.server.domain.order.OrderProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderProductJpaRepository : JpaRepository<OrderProductEntity, Long> {

    fun findAllByOrderId(orderId: Long): List<OrderProductEntity>
}