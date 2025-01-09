package kr.hhplus.be.server.domain.product


interface ProductInventoryRepository {

    // 재고 업데이트 [ 증가, 감소 ]
    fun inventoryUpdate(productId: Long, inventory: Int)

    fun findById(productId: Long): ProductInventoryEntity?
}