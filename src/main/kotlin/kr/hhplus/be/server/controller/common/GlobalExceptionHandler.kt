package kr.hhplus.be.server.controller.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    // 표준 Exception은 GlobalExceptionHandler에서 처리
    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerIllegalArgumentException(iae: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.value().toString(), iae.message ?: ""))
    }
}
