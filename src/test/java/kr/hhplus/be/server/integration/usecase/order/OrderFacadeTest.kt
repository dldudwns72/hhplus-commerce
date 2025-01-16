package kr.hhplus.be.server.integration.usecase.order

import kr.hhplus.be.server.application.coupon.CouponService
import kr.hhplus.be.server.application.order.OrderFacade
import kr.hhplus.be.server.controller.order.dto.OrderProductRequest
import kr.hhplus.be.server.controller.order.dto.request.OrderRequest
import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.order.OrderEntity
import kr.hhplus.be.server.domain.order.OrderService
import kr.hhplus.be.server.domain.payment.PaymentEntity
import kr.hhplus.be.server.domain.payment.PaymentService
import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.product.ProductService
import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.domain.user.UserService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderFacadeTest {

    @Autowired
    lateinit var orderFacade: OrderFacade

    @Mock
    lateinit var orderService: OrderService

    @Mock
    lateinit var productService: ProductService

    @Mock
    lateinit var couponService: CouponService

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var paymentService: PaymentService

    @Test
    @DisplayName("쿠폰과 함께 상품 주문")
    fun successOrderWithCoupon() {
        // Given
        val userId = 1L
        val orderRequest = OrderRequest(
            couponId = 1L,
            orderProducts = listOf(OrderProductRequest(1, 2))
        )

        val user = mock(UserEntity::class.java)
        val coupon = mock(CouponEntity::class.java)
        val product = mock(ProductEntity::class.java)
        val order = mock(OrderEntity::class.java)
        val payment = mock(PaymentEntity::class.java)

        `when`(userService.getUserById(userId)).thenReturn(user)
        `when`(couponService.getCoupon(orderRequest.couponId!!)).thenReturn(coupon)
        `when`(productService.getProductWithLock(orderRequest.orderProducts[0].productId)).thenReturn(product)

        val totalAmount = 100L
        `when`(orderService.order(user, coupon, listOf(Pair(product,2)))).thenReturn(order)
        `when`(paymentService.pay(order, totalAmount)).thenReturn(payment)
        `when`(paymentService.isSuccess(any())).thenReturn(true)

        // When
        val result = orderFacade.order(userId, orderRequest)

        // Then
        assertNotNull(result)
        verify(orderService).order(user, coupon, listOf(Pair(product,2))) // OrderService의 order 메서드 호출 검증
        verify(paymentService).pay(order, totalAmount)  // PaymentService의 pay 메서드 호출 검증
        verify(paymentService).isSuccess(any())  // PaymentService의 isSuccess 메서드 호출 검증
        verify(order).complete(totalAmount)  // OrderResult의 complete 메서드 호출 검증
    }

    @Test
    @DisplayName("쿠폰 없이 주문")
    fun successOrderWithOutCoupon() {
        // Given
        val userId = 1L
        val orderRequest = OrderRequest(
            couponId = null,
            orderProducts = listOf(OrderProductRequest(1, 2))
        )

        val user = mock(UserEntity::class.java)
        val product = mock(ProductEntity::class.java)
        val order = mock(OrderEntity::class.java)
        val payment = mock(PaymentEntity::class.java)

        `when`(userService.getUserById(userId)).thenReturn(user)
        `when`(productService.getProductWithLock(orderRequest.orderProducts[0].productId)).thenReturn(product)

        val totalAmount = 100L
        `when`(orderService.order(user, null, listOf(Pair(product,2)))).thenReturn(order)
        `when`(paymentService.pay(order, totalAmount)).thenReturn(payment)
        `when`(paymentService.isSuccess(any())).thenReturn(true)

        // When
        val result = orderFacade.order(userId, orderRequest)

        // Then
        assertNotNull(result)
        verify(orderService).order(user, null, listOf(Pair(product,2))) // 쿠폰 없이 주문 처리 확인
    }

    @Test
    @DisplayName("주문 실패")
    fun failOrder() {
        // Given
        val userId = 1L
        val orderRequest = OrderRequest(
            couponId = 1L,
            orderProducts = listOf(OrderProductRequest(1, 2))
        )

        val user = mock(UserEntity::class.java)
        val coupon = mock(CouponEntity::class.java)
        val product = mock(ProductEntity::class.java)
        val order = mock(OrderEntity::class.java)
        val payment = mock(PaymentEntity::class.java)

        `when`(userService.getUserById(userId)).thenReturn(user)
        `when`(couponService.getCoupon(orderRequest.couponId!!)).thenReturn(coupon)
        `when`(productService.getProductWithLock(orderRequest.orderProducts[0].productId)).thenReturn(product)

        val totalAmount = 100L
        `when`(orderService.order(user, coupon, listOf(Pair(product,2)))).thenReturn(order)
        `when`(paymentService.pay(order, totalAmount)).thenReturn(payment)
        `when`(paymentService.isSuccess(any())).thenReturn(false)  // 결제 실패 시

        // When
        val result = orderFacade.order(userId, orderRequest)

        // Then
        assertNotNull(result)
        verify(paymentService).pay(order, totalAmount)  // 결제 시도 확인
        verify(paymentService).isSuccess(any())  // 결제 상태 확인
        verify(order, never()).complete(any())  // 결제 실패 시 완료되지 않음
    }
}
