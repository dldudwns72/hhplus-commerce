package kr.hhplus.be.server.order

import kr.hhplus.be.server.application.order.OrderService
import kr.hhplus.be.server.domain.order.OrderRepository
import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.product.ProductInventoryEntity
import kr.hhplus.be.server.domain.product.ProductRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class OrderTest {
    @InjectMocks
    private lateinit var orderService: OrderService

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var orderRepository: OrderRepository

    @Test
    @DisplayName("주문 시 상품 재고가 없을 경우 예외를 발생시킨다.")
    fun emptyStock() {

        val productId = 1L
        val mockProductInventory = ProductInventoryEntity(
            productId = productId,
            inventory = 10
        )
        // orderService.order()

        val mockProduct = ProductEntity(
            name = "상품1",
            price = 100
        )
//        `when`(productRepository.findProductById(productId)).thenReturn(mockProduct)




        assertThrows<IllegalArgumentException> {

        }
    }

    @Test
    @DisplayName("상품 주문")
    fun productOrder() {

    }
}