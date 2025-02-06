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
        count(op.id)
    )
    from ProductEntity p
    INNER JOIN ProductInventoryEntity pi ON p.id = pi.product.id
    INNER JOIN OrderProductEntity op ON op.product.id = p.id
    INNER JOIN OrderEntity o ON o.id = op.order.id
    WHERE op.createdAt >= :startDate AND op.createdAt <= :endDate
    AND o.status = 'COMPLETED'
    GROUP BY p.id, pi.inventory
"""
    )
    fun findPopularProducts(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<PopularProductResult>


}