package kr.hhplus.be.server.infra.order

import kr.hhplus.be.server.domain.order.OrderProductEntity
import kr.hhplus.be.server.domain.order.OrderProductResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderProductJpaRepository : JpaRepository<OrderProductEntity, Long> {

    @Query("select OrderProductEntity(op.orderId, op.productId,p.name,p.price) from OrderProductEntity op INNER JOIN ProductEntity p ON p.id = op.productId INNER JOIN OrderEntity o ON o.id = op.orderId WHERE o.userId = :userId")
    fun findOrderProductEntityByUserId(@Param("userId") userId: Long): List<OrderProductResult>
}