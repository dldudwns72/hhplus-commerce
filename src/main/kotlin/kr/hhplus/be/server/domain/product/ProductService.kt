package kr.hhplus.be.server.domain.product

import jakarta.transaction.Transactional
import kr.hhplus.be.server.controller.product.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun getProduct(productId: Long): ProductEntity {
        return productRepository.findProductById(productId)
            ?: throw IllegalArgumentException("Product with id $productId not found")
    }

    fun getProductWithLock(productId: Long): ProductEntity {
        return productRepository.findProductWithLock(productId)
            ?: throw IllegalArgumentException("Product with id $productId not found")
    }

    fun getProducts(pageable: Pageable): Page<ProductResponse> {
        val product = productRepository.findAll(pageable)
        return product.map { it.toProductResponse() }
    }

    fun getPopularProduct(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        @PageableDefault(size = 5) pageable: Pageable
    ): Page<PopularProductResponse> {
        return productRepository.findPopularProduct(startDate, endDate, pageable).map {
            it.toPopularProductResponse()
        }
    }

    @Transactional
    fun decreaseStock(productId: Long) {
        val product = productRepository.findProductById(productId)
            ?: throw IllegalArgumentException("Product with id $productId not found")
        product.productInventory.decreaseInventoryCount()
    }
}