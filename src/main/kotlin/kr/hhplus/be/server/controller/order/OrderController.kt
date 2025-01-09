package kr.hhplus.be.server.controller.order

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.application.order.OrderService
import kr.hhplus.be.server.controller.order.dto.OrderRequest
import kr.hhplus.be.server.controller.order.dto.OrderResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/order")
@Tag(name = "주문 API")
class OrderController(private val orderService: OrderService) {

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
            )
        ]
    )
    @PostMapping("/user/{userId}")
    fun postOrder(
        @PathVariable userId: Long,
        @RequestBody orderRequests: List<OrderRequest>
    ): ResponseEntity<OrderResponse> {
        return ResponseEntity(orderService.order(userId, orderRequests), HttpStatus.OK)
    }
}