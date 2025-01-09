package kr.hhplus.be.server.domain.order

interface OrderProductRepository {
    fun saveAll(orderProducts: List<OrderProductEntity>)
    fun findByUserId(userId: Long): List<OrderProductResult>
}