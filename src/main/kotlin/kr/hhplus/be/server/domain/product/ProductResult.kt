package kr.hhplus.be.server.domain.product


data class ProductResult(
    val id: Long,
    val name: String,
    val price: Long,
    val inventory: Int
) {
    fun addInventory(): Int {
        return inventory + 1
    }

    fun decreaseInventory(): Int {
        if(inventory == 0) throw IllegalArgumentException("상품 재고가 존재하지 않습니다.")
        return inventory - 1
    }

}