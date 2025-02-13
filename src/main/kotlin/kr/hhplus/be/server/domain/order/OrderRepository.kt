package kr.hhplus.be.server.domain.order

interface OrderRepository {

    fun findByUserId(userId: Long): List<OrderEntity>
    fun saveOrder(orderEntity: OrderEntity): OrderEntity
    fun updateOrderStatus(id: Long, status: OrderStatus)
}