package kr.hhplus.be.server.infra.product

import jakarta.persistence.LockModeType
import jakarta.transaction.Transactional
import kr.hhplus.be.server.domain.product.PopularProductResult
import kr.hhplus.be.server.domain.product.ProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime


interface ProductJpaRepository : JpaRepository<ProductEntity, Long> {

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductEntity p WHERE p.id = :id")
    fun findProductWithLock(@Param("id") id: Long): ProductEntity?


    @Query(
        """
    select new kr.hhplus.be.server.domain.product.PopularProductResult(
        p.id,
        p.name,
        p.price,
        pi.inventory,
        SUM(op.quantity)
    )
    from OrderEntity o
    INNER JOIN OrderProductEntity op ON o.id = op.order.id
    INNER JOIN ProductEntity p ON op.product.id = p.id
    INNER JOIN ProductInventoryEntity pi ON p.id = pi.product.id
    WHERE op.createdAt >= :startDate AND op.createdAt <= :endDate
    AND o.status = 'COMPLETED'
    GROUP BY p.id, pi.inventory
    ORDER BY SUM(op.quantity) DESC
    LIMIT :limitCount
"""
    )
    fun findPopularProducts(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        limitCount: Int
    ): List<PopularProductResult>


}