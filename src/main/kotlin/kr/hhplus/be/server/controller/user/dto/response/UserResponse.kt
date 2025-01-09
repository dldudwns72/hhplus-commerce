package kr.hhplus.be.server.controller.user.dto.response

import kr.hhplus.be.server.domain.user.UserEntity


data class UserResponse(
    val id: Long,
    val name: String,
    val balance: Long
)

fun UserEntity.toResponse() = UserResponse(id, name, balance)