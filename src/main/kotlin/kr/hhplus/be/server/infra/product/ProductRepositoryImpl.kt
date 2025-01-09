package kr.hhplus.be.server.infra.product

import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.product.ProductRepository
import kr.hhplus.be.server.domain.product.ProductResult
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository
) : ProductRepository {
    override fun findProduct(id: Long): ProductResult {
        return productJpaRepository.findProduct(id)
    }

    override fun findProductById(id: Long): ProductEntity? {
        return productJpaRepository.findById(id).getOrNull()
    }

    override fun findProducts(pageable: Pageable): Page<ProductResult> {
        // 페이징 처리
        return productJpaRepository.findAllProducts(pageable)
    }

    override fun findPopularProduct(): List<ProductEntity> { // 이건 반환값 체크 해보자, 상품 판매 개수 등등
        return productJpaRepository.findAll()
    }
}