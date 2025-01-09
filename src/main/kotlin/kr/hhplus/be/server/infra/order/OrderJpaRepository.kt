package kr.hhplus.be.server.infra.order

import kr.hhplus.be.server.domain.order.OrderEntity
import kr.hhplus.be.server.domain.order.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface OrderJpaRepository : JpaRepository<OrderEntity, Long> {

    fun findOrderByUserId(userId: Long): List<OrderEntity>

    @Query("UPDATE OrderEntity o SET o.status = :status where o.id = :id")
    @Modifying
    fun updateOrderStatus(id:Long, status: OrderStatus)
}