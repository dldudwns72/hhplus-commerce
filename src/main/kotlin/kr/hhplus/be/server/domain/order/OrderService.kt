package kr.hhplus.be.server.domain.order

import jakarta.transaction.Transactional
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import kr.hhplus.be.server.domain.order.event.OrderCanceledEvent
import kr.hhplus.be.server.domain.order.event.OrderCompletedEvent
import kr.hhplus.be.server.domain.order.event.OrderCreateEvent
import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.product.ProductRepository
import kr.hhplus.be.server.domain.user.UserEntity
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderProductRepository: OrderProductRepository,
    private val productRepository: ProductRepository,
    private val redisTemplate: RedisTemplate<String, String>,
    private val eventPublisher: ApplicationEventPublisher
) {

    @Transactional
    fun order(user: UserEntity, couponUser: CouponUserEntity?, products: List<Pair<ProductEntity, Int>>): OrderEntity {
        val order = couponUser?.let { OrderEntity(user = user, coupon = it.coupon) } ?: run { OrderEntity(user = user) }
        val orderProducts = products.map { product ->
            try {
                product.first.productInventory.decreaseInventoryCount()
            } catch (e: OptimisticLockingFailureException) {
                throw IllegalStateException("상품 재고 업데이트에 실패하였습니다. 상품 ID: ${product.first.id}")
            }
            OrderProductEntity(
                quantity = product.second,
                order = order,
                product = product.first
            )
        }.toMutableList()
        order.orderProducts = orderProducts
        order.totalAmount = orderProducts.sumOf {
            it.product.price * it.quantity
        }

        eventPublisher.publishEvent(OrderCreateEvent(order))

        return orderRepository.saveOrder(order)
    }


    @Async
    @EventListener
    fun handleOrderCompletedEvent(event: OrderCompletedEvent) {
        val orderProducts = orderProductRepository.findAllByOrderId(event.order.id)
        // 인기 상품 check
        orderProducts.forEach { orderProduct ->
            val zCard = redisTemplate.opsForZSet().zCard("popular_products")
            if (zCard != null && zCard > 0) {
                redisTemplate.opsForZSet()
                    .incrementScore(
                        "popular_products",
                        orderProduct.product.id.toString(),
                        orderProduct.quantity.toDouble()
                    )
            } else {
                redisTemplate.opsForZSet()
                    .add("popular_products", orderProduct.product.id.toString(), orderProduct.quantity.toDouble())
            }
        }
        event.order.complete()
    }

    @Async
    @EventListener
    fun handleOrderCanceledEvent(event: OrderCanceledEvent) {
        val orderProducts = orderProductRepository.findAllByOrderId(event.orderId)
        orderProducts.forEach { orderProduct ->
            orderProduct.product.productInventory.increaseInventoryCount(orderProduct.quantity)
            productRepository.save(orderProduct.product)
        }
    }
}
