package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.order.OrderService
import kr.hhplus.be.server.domain.order.OrderRepository
import kr.hhplus.be.server.domain.product.ProductRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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

    }

    @Test
    @DisplayName("상품 주문")
    fun productOrder() {
        // 주문 진행
        //

    }
}