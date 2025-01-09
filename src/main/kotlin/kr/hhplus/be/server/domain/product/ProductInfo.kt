package kr.hhplus.be.server.domain.product


data class ProductInfo(
    val id: Long,
    val name: String,
    val price: Long,
    val inventory: Int
) {

}