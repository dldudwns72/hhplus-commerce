package kr.hhplus.be.server.domain.order

import jakarta.transaction.Transactional
import kr.hhplus.be.server.application.order.OrderResult
import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.user.UserEntity
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    @Transactional
    fun order(user: UserEntity, coupon: CouponEntity?, products: List<Pair<ProductEntity, Int>>): OrderEntity {
        val order = coupon?.let { OrderEntity(user, coupon) } ?: run { OrderEntity(user) }
        val orderProducts = products.map { product ->
            product.first.productInventory.decreaseInventoryCount()
            OrderProductEntity(
                quantity = product.second,
                order = order,
                product = product.first
            )
        }
        orderProducts.forEach { order.addOrderProduct(it) }
        return orderRepository.saveOrder(order)
    }
}
