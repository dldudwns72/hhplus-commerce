package kr.hhplus.be.server.controller.common

data class ErrorResponse(
    val errorCode: String,
    val errorMessage: String
) {
}