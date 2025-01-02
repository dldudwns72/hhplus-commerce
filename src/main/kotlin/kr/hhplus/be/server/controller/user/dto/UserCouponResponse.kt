package kr.hhplus.be.server.controller.user.dto


data class UserCouponResponse(
    val id:Long,
    val couponId: List<Long>
) {
}