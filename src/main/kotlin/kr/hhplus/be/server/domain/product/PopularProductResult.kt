package kr.hhplus.be.server.domain.product

data class PopularProductResult(
    val id: Long,
    val name: String,
    val price: Long,
    val inventory: Int,
    val orderCount: Long // count 반환은 Long 타입으로 해야한다.
)