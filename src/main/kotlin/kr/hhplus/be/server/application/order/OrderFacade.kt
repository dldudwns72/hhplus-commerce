package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.application.coupon.CouponService
import kr.hhplus.be.server.controller.order.dto.request.OrderRequest
import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.order.OrderEntity
import kr.hhplus.be.server.domain.order.OrderResult
import kr.hhplus.be.server.domain.order.OrderService
import kr.hhplus.be.server.domain.order.toResult
import kr.hhplus.be.server.domain.payment.PaymentService
import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.product.ProductService
import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class OrderFacade(
    private val orderService: OrderService,
    private val productService: ProductService,
    private val couponService: CouponService,
    private val userService: UserService,
    private val paymentService: PaymentService,
) {

    fun order(userId: Long, request: OrderRequest): OrderResult {
        // 유저 조회
        val user: UserEntity = userService.getUserById(userId)

        // 쿠폰 조회 nullable
        val coupon: CouponEntity? = request.couponId?.let { couponId ->
            couponService.getCoupon(couponId)
        }

        // 주문할 상품 조회
        val products: List<ProductEntity> = request.orderProducts.map {
            productService.getProductWithLock(it.productId)
        }

        val order = orderService.order(user, coupon, products)

        val totalAmount: Long = order.orderProducts.sumOf {
            it.product.price * it.quantity
        }

        val payment = paymentService.pay(order, totalAmount)

        if (paymentService.isSuccess(payment)) {
            order.complete(totalAmount)
        }
        return order.toResult()
    }
}