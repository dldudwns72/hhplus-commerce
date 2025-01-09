package kr.hhplus.be.server.infra.product

import kr.hhplus.be.server.domain.product.PopularProductResult
import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.product.ProductRepository
import kr.hhplus.be.server.domain.product.ProductInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository
) : ProductRepository {

    override fun findProductById(id: Long): ProductEntity? {
        return productJpaRepository.findById(id).getOrNull()
    }

    override fun findProductWithLock(id: Long): ProductEntity? {
        return productJpaRepository.findProductWithLock(id)
    }

    override fun findAllByIds(ids: List<Long>): List<ProductEntity> {
        return productJpaRepository.findAllById(ids)
    }

    override fun findAll(pageable: Pageable): Page<ProductEntity> {
        return productJpaRepository.findAll(pageable)
    }

    override fun findPopularProduct(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        pageable: Pageable
    ): Page<PopularProductResult> {
        return productJpaRepository.findPopularProducts(startDate, endDate, pageable)
    }
}