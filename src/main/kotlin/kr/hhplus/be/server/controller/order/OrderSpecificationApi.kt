package kr.hhplus.be.server.controller.order

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.controller.common.SingleResponse
import kr.hhplus.be.server.controller.coupon.dto.response.CouponResponse
import kr.hhplus.be.server.controller.order.dto.request.OrderRequest
import kr.hhplus.be.server.controller.order.dto.response.OrderResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "주문 API")
interface OrderSpecificationApi {

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
    fun postOrder(
        userId: Long,
        orderRequest: OrderRequest
    ): ResponseEntity<SingleResponse<OrderResponse>>
}