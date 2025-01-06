package kr.hhplus.be.server.controller.order

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.controller.common.ApiErrorResponse
import kr.hhplus.be.server.controller.order.dto.OrderRequest
import kr.hhplus.be.server.controller.order.dto.OrderResponse
import kr.hhplus.be.server.controller.user.dto.UserResponse
import org.springframework.context.annotation.Description
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/order")
@Tag(name = "주문 API")
class OrderController {

    @Operation(summary = "상품 주문 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "주문 성공", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = OrderResponse::class))
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400", description = "주문 실패", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = OrderResponse::class))
                    )
                ]
            )
        ]
    )
    @PostMapping("/user/{userId}")
    fun postOrder(
        @PathVariable userId: Long,
        @RequestBody orderRequests: List<OrderRequest>
    ): ResponseEntity<OrderResponse> {
        orderRequests.forEach { request ->
            if (request.quantity <= 0) {
                throw IllegalStateException("상품 재고가 존재하지 않습니다.")
            }
            if (userId == 1L) { // 잔액이 부족한 유자
                throw IllegalStateException("상품 금액보다 잔액이 부족합니다.")
            }
        }
        return ResponseEntity(HttpStatus.OK)
    }
}