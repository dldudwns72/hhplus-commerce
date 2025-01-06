package kr.hhplus.be.server.controller.common

data class ApiErrorResponse<T>(
    val errorCode: String,
    val errorMessage: String
) {
}