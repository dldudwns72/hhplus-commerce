package kr.hhplus.be.server.infra.order

import kr.hhplus.be.server.domain.order.OrderProductEntity
import kr.hhplus.be.server.domain.order.OrderProductResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderProductJpaRepository : JpaRepository<OrderProductEntity, Long> {
}