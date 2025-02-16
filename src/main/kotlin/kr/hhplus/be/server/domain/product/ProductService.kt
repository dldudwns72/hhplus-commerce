package kr.hhplus.be.server.domain.product

import jakarta.transaction.Transactional
import kr.hhplus.be.server.controller.product.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val redisTemplate: RedisTemplate<String, String>
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
    ): List<PopularProductResponse> {
        val limitCount = 4
        val popularProducts = redisTemplate.opsForZSet().reverseRangeWithScores(
            "popular_products",
            0,
            limitCount.toLong()
        )?.map {
            Pair(it.value!!.toLong(), it.score ?: 0.0)
        } ?: emptyList()

        return runCatching {
            popularProducts.mapNotNull { (productId, orderCount) ->
                productRepository.findProductById(productId)?.let { product ->
                    PopularProductResult(
                        id = product.id,
                        name = product.name,
                        price = product.price,
                        inventory = product.productInventory.inventory,
                        orderCount = orderCount.toLong()
                    )
                }?.toPopularProductResponse()
            }
        }.getOrElse {
            productRepository.findPopularProduct(startDate, endDate, limitCount).map {
                it.toPopularProductResponse()
            }
        }
    }

    @Transactional
    fun decreaseStock(productId: Long) {
        val product = productRepository.findProductById(productId)
            ?: throw IllegalArgumentException("Product with id $productId not found")
        product.productInventory.decreaseInventoryCount()
    }
}