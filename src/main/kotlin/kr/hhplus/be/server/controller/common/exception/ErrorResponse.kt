package kr.hhplus.be.server.controller.common.exception

data class ErrorResponse(
    val errorCode: String,
    val errorMessage: String
) {
}