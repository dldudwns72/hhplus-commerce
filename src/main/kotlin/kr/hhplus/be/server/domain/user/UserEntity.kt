package kr.hhplus.be.server.domain.user

import kr.hhplus.be.server.controller.user.dto.BalanceRequest
import kr.hhplus.be.server.domain.common.BaseEntity

class UserEntity(
    val name: String,
    val balance: Int,
) : BaseEntity() {
}