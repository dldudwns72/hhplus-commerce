package kr.hhplus.be.server.domain.order

import jakarta.transaction.Transactional
import kr.hhplus.be.server.application.order.OrderResult
import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.user.UserEntity
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderProductRepository: OrderProductRepository
) {

    @Transactional
    fun order(user: UserEntity, coupon: CouponEntity?, products: List<ProductEntity>): OrderEntity {
        val order = coupon?.let { OrderEntity(user, coupon) } ?: run { OrderEntity(user) }
        val orderProducts = products.map { product ->
            product.productInventory.decreaseInventoryCount()
            OrderProductEntity(
                quantity = products.associateBy { it.id }.size,
                order = order,
                product = product
            )
        }
        order.orderProducts.addAll(orderProducts)
        orderRepository.saveOrder(order)
        return order
    }

    fun success(order: OrderEntity): OrderEntity {
        order.status = OrderStatus.COMPLETED
        return order
    }
}
