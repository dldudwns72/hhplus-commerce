package kr.hhplus.be.server.application.order

import jakarta.transaction.Transactional
import kr.hhplus.be.server.application.coupon.CouponService
import kr.hhplus.be.server.controller.order.dto.request.OrderRequest
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import kr.hhplus.be.server.domain.order.OrderResult
import kr.hhplus.be.server.domain.order.OrderService
import kr.hhplus.be.server.domain.order.toResult
import kr.hhplus.be.server.domain.payment.PaymentService
import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.product.ProductService
import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.domain.user.UserService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class OrderFacade(
    private val orderService: OrderService,
    private val productService: ProductService,
    private val couponService: CouponService,
    private val userService: UserService,
    private val paymentService: PaymentService,
    private val redisTemplate: RedisTemplate<String, String>
) {
    @Transactional
    fun order(userId: Long, request: OrderRequest): OrderResult {
        // 유저 조회
        val user: UserEntity = userService.getUserById(userId)

        // 쿠폰 조회 nullable, 주문당 쿠폰 1개 사용 가능,
        // coupon_id를 받아야하나.. 유저가 선택한 쿠폰을 요청받는건데, coupon_user 에서 불필요한 coupon 조회가 일어나는건가?
        val coupon: CouponUserEntity? = request.couponUserId?.let { couponId ->
            couponService.getCouponsUser(couponId)
        }

        val products: List<Pair<ProductEntity, Int>> = request.orderProducts.map {
            productService.getProductWithLock(it.productId) to it.quantity
        }

        val order = orderService.order(user, coupon, products)

        val totalAmount: Long = order.orderProducts.sumOf {
            it.product.price * it.quantity
        }
        user.usePoint(totalAmount)

        val payment = paymentService.pay(order, totalAmount)

        if (paymentService.isSuccess(payment)) {
            order.complete(totalAmount)
            products.forEach { product ->
                val zCard = redisTemplate.opsForZSet().zCard("popular_products")
                if (zCard != null && zCard > 0) {
                    redisTemplate.opsForZSet()
                        .incrementScore("popular_products", product.first.id.toString(), product.second.toDouble())
                } else {
                    redisTemplate.opsForZSet()
                        .add("popular_products", product.first.id.toString(), product.second.toDouble())
                }
            }
        }
        return order.toResult()
    }
}