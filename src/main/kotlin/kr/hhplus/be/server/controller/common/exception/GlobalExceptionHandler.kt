package kr.hhplus.be.server.controller.common.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    val logger = LoggerFactory.getLogger("Error Logger")


    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerIllegalArgumentException(iae: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.value().toString(), iae.message ?: ""))
    }


    @ExceptionHandler(ProductException::class)
    fun handlerProductException(pe: ProductException): ResponseEntity<ErrorResponse> {
        logger.error(pe.message)
        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.value().toString(), pe.message ?: ""))
    }

    @ExceptionHandler(OrderException::class)
    fun handlerOrderException(oe: OrderException): ResponseEntity<ErrorResponse> {
        logger.error(oe.message)
        // 주문 오류일 경우 별도 알람을 보내는 기능을 수행하는 로직을 넣는다?
        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.value().toString(), oe.message ?: ""))
    }

}
