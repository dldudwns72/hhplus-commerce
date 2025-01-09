package kr.hhplus.be.server.product

import kr.hhplus.be.server.application.product.ProductService
import kr.hhplus.be.server.application.user.UserService
import kr.hhplus.be.server.domain.product.ProductInventoryRepository
import kr.hhplus.be.server.domain.product.ProductRepository
import kr.hhplus.be.server.domain.product.ProductResult
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ProductTest {

    @InjectMocks
    private lateinit var productService: ProductService

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var productInventoryRepository: ProductInventoryRepository


    @Test
    @DisplayName("상품 조회 성공")
    fun getProduct() {
        val productId = 1L
        val mockProduct = ProductResult(
            productId,
            "product",
            price = 100,
            inventory = 100
        )
        `when`(productRepository.findProduct(productId)).thenReturn(mockProduct)
        val response = productService.getProduct(productId)

        Assertions.assertTrue(response.inventory == 100)
    }

    @Test
    @DisplayName("상품 재고 감소")
    fun decreaseStockProduct() {
        val productId = 1L
        val mockProduct = ProductResult(
            productId,
            "product",
            price = 100,
            inventory = 100
        )

        `when`(productRepository.findProduct(productId)).thenReturn(mockProduct)

        productService.decreaseStock(productId)
        verify(productInventoryRepository).inventoryUpdate(productId, 99)
    }

    @Test
    @DisplayName("상품 재고가 없을 때 재고 차감 시 예외 발생")
    fun emptyInventory() {
        val productId = 1L
        val mockProduct = ProductResult(
            productId,
            "product",
            price = 100,
            inventory = 0
        )

        `when`(productRepository.findProduct(productId)).thenReturn(mockProduct)

        assertThrows<IllegalArgumentException> {
            productService.decreaseStock(productId)
        }
    }


}