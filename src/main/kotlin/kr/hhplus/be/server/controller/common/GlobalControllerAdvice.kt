package kr.hhplus.be.server.controller.common

import org.springdoc.api.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import kotlin.RuntimeException
import kotlin.coroutines.RestrictsSuspension


@RestrictsSuspension
class GlobalControllerAdvice {

    @ExceptionHandler(RuntimeException::class)
    fun handlerRuntimeException(ex: RuntimeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.value().toString(), ex.message ?: ""))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerIllegalArgumentException(iae: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.value().toString(), iae.message ?: ""))
    }
}