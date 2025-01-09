package kr.hhplus.be.server.domain.product

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductRepository {
    fun findProduct(id: Long): ProductResult?
    fun findProductById(id: Long): ProductEntity?
    fun findProducts(pageable: Pageable): Page<ProductResult>
    fun findPopularProduct(): List<ProductEntity>
}