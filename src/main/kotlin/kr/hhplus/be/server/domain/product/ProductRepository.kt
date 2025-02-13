package kr.hhplus.be.server.domain.product

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface ProductRepository {
    fun findProductById(id: Long): ProductEntity?
    fun findProductWithLock(id: Long): ProductEntity?
    fun findAllByIds(ids: List<Long>): List<ProductEntity>
    fun findAll(pageable: Pageable): Page<ProductEntity>
    fun findPopularProduct(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        limitCount: Int
    ): List<PopularProductResult>
}