package kr.hhplus.be.server.application.product

import kr.hhplus.be.server.controller.product.dto.PopularProductResponse
import kr.hhplus.be.server.controller.product.dto.ProductResponse
import kr.hhplus.be.server.controller.product.dto.toProductResponse
import kr.hhplus.be.server.domain.product.ProductInventoryRepository
import kr.hhplus.be.server.domain.product.ProductRepository
import kr.hhplus.be.server.domain.product.ProductResult
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productInventoryRepository: ProductInventoryRepository
) {
    fun getProduct(productId: Long): ProductResponse {
        val product = productRepository.findProduct(productId)
            ?: throw IllegalArgumentException("Product with id $productId not found")
        return product.toProductResponse()
    }

    fun getProducts(page: Int, size: Int): Page<ProductResponse> {
        val pageable = PageRequest.of(page, size)
        val product = productRepository.findProducts(pageable)
        return product.map { it.toProductResponse() }
    }

    fun getPopularProduct(startDate: String?, endDate: String?, page: Int, size: Int): List<PopularProductResponse> {
        // 인기 상품 조회
        // 주문을 해야 상품 조회를 하니 마지막으로 체크
        return listOf()
    }

    fun decreaseStock(productId: Long) {
        val product: ProductResult = productRepository.findProduct(productId)
            ?: throw IllegalArgumentException("Product with id $productId not found")

        productInventoryRepository.inventoryUpdate(product.id, product.decreaseInventory())
    }
}