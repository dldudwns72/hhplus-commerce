package kr.hhplus.be.server.application.order

import jakarta.transaction.Transactional
import kr.hhplus.be.server.application.coupon.CouponService
import kr.hhplus.be.server.controller.order.dto.request.OrderRequest
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import kr.hhplus.be.server.domain.order.OrderResult
import kr.hhplus.be.server.domain.order.OrderService
import kr.hhplus.be.server.domain.order.toResult
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
) {
    @Transactional
    fun order(userId: Long, request: OrderRequest): OrderResult {
        // 유저 조회
        val user: UserEntity = userService.getUserById(userId)

        val coupon: CouponUserEntity? = request.couponUserId?.let { couponId ->
            couponService.getCouponsUser(couponId)
        }

        val products: List<Pair<ProductEntity, Int>> = request.orderProducts.map {
            productService.getProductWithLock(it.productId) to it.quantity
        }

        // 주문 생성
        return orderService.order(user, coupon, products).toResult()
    }
}