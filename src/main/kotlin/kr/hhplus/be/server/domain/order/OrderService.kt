package kr.hhplus.be.server.domain.order

import jakarta.transaction.Transactional
import kr.hhplus.be.server.controller.common.ProductException
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import kr.hhplus.be.server.domain.product.ProductEntity
import kr.hhplus.be.server.domain.user.UserEntity
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    @Transactional
    fun order(user: UserEntity, couponUser: CouponUserEntity?, products: List<Pair<ProductEntity, Int>>): OrderEntity {
        val order = couponUser?.let { OrderEntity(user = user, coupon = it.coupon) } ?: run { OrderEntity(user = user) }
        val orderProducts = products.map { product ->
            try {
                product.first.productInventory.decreaseInventoryCount()
            } catch (e: OptimisticLockingFailureException) {
                throw IllegalStateException("상품 재고 업데이트에 실패하였습니다.")
            }
            OrderProductEntity(
                quantity = product.second,
                order = order,
                product = product.first
            )
        }.toMutableList()
        order.orderProducts = orderProducts
        return orderRepository.saveOrder(order)
    }
}
