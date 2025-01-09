package kr.hhplus.be.server.controller.user.dto.request


data class BalanceRequest(
    val point: Long
) {
    init {
        if (point <= 0) throw IllegalArgumentException("충전 포인트는 0 이상이여야 합니다.")
    }
}