package kr.hhplus.be.server.controller.user.dto


data class UserBalanceResponse(
    val userId:Long,
    val orders: List<Long>
) {
}