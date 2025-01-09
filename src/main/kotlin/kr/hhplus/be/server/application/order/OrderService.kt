package kr.hhplus.be.server.application.order

import jakarta.transaction.Transactional
import kr.hhplus.be.server.controller.order.dto.OrderRequest
import kr.hhplus.be.server.controller.order.dto.OrderResponse
import kr.hhplus.be.server.domain.coupon.CouponDiscountType
import kr.hhplus.be.server.domain.coupon.CouponRepository
import kr.hhplus.be.server.domain.coupon.IssuedCouponResult
import kr.hhplus.be.server.domain.order.*
import kr.hhplus.be.server.domain.product.ProductInventoryRepository
import kr.hhplus.be.server.domain.product.ProductRepository
import org.springframework.stereotype.Service
import kotlin.properties.Delegates

@Service
class OrderService(
    private val productRepository: ProductRepository,
    private val productInventoryRepository: ProductInventoryRepository,
//    private val productService: ProductService // OrderFacade -> orderService, paymentService, CouponService, productService
    private val orderRepository: OrderRepository,
    private val orderProductRepository: OrderProductRepository,
    private val couponRepository: CouponRepository
) {

    @Transactional
    fun order(userId: Long, request: List<OrderRequest>): OrderResponse {
        val order: OrderEntity = orderRepository.saveOrder(OrderEntity(userId))
        var totalAmount = 0L
        val orderProducts = request.map {
            val product = productRepository.findProductById(it.productId)
                ?: throw IllegalArgumentException("Product with id ${it.productId} not found")

            // 재고 파악
            val productInventory = productInventoryRepository.findById(product.id)
                ?: throw IllegalArgumentException("Product with id ${product.id} not found")

            // 재고 감소
            productInventory.decreaseInventory()
            // 재고 업데이트
            productInventoryRepository.inventoryUpdate(it.productId, productInventory.inventory)

            val issuedUserCoupon: IssuedCouponResult? = it.couponId?.let { couponId ->
                // 유저는 1개의 쿠폰만 발급 받을 수 있다.
                couponRepository.findCouponByCouponIdAndUserId(couponId, userId)
            }

            val paymentAmount = issuedUserCoupon?.let {
                if (issuedUserCoupon.discountType == CouponDiscountType.PERCENT) {
                    product.price * (1 - issuedUserCoupon.discountValue / 100) // 10은 쿠폰 할인율 couponPolicy.discountValue
                } else {
                    product.price - issuedUserCoupon.discountValue
                }
            } ?: product.price

            totalAmount += product.price

            OrderProductEntity(
                orderId = order.id,
                productId = product.id,
                count = it.quantity
            )
        }
        orderProductRepository.saveAll(orderProducts)

        // 결제 할 떄 일괄 처리
        if (DataPlatform.send(true)) {
            order.successOrder(totalAmount)
            orderRepository.saveOrder(order)
        } else {
            throw IllegalArgumentException("주문에 실패하였습니다.")
        }

        return OrderResponse(
            orderId = order.id,
            totalAmount = totalAmount,
            status = order.status
        )

    }
}