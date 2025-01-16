package kr.hhplus.be.server.domain.product

import kr.hhplus.be.server.controller.common.exception.ProductException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ProductTest {

    @InjectMocks
    private lateinit var productService: ProductService

    @Mock
    private lateinit var productRepository: ProductRepository


    @Test
    @DisplayName("상품 조회 성공")
    fun getProduct() {
        val productId = 1L
        val mockProduct = ProductEntity(
            "product1",
            price = 100,
            productInventory = ProductInventoryEntity(
                inventory = 100
            ),
        )
        `when`(productRepository.findProductById(productId)).thenReturn(mockProduct)
        val product = productService.getProduct(productId)

        Assertions.assertTrue(product.productInventory.inventory == 100)
    }

    @Test
    @DisplayName("상품 재고 감소")
    fun decreaseStockProduct() {
        val productId = 1L
        val mockProduct = ProductEntity(
            "product1",
            price = 100,
            productInventory = ProductInventoryEntity(
                inventory = 100
            ),
        )
        `when`(productRepository.findProductById(productId)).thenReturn(mockProduct)

        productService.decreaseStock(productId)
        val product = productService.getProduct(productId)
        Assertions.assertTrue(product.productInventory.inventory == 100)

    }

    @Test
    @DisplayName("상품 재고가 없을 때 재고 차감 시 예외 발생")
    fun emptyInventory() {
        val productId = 1L
        val mockProduct = ProductEntity(
            "product1",
            price = 100,
            productInventory = ProductInventoryEntity(
                inventory = 0
            ),
        )
        `when`(productRepository.findProductById(productId)).thenReturn(mockProduct)

        assertThrows<ProductException> {
            productService.decreaseStock(productId)
        }
    }


}