package kr.hhplus.be.server.controller.order

import kr.hhplus.be.server.controller.order.dto.OrderRequest
import kr.hhplus.be.server.controller.order.dto.OrderResponse
import org.springframework.context.annotation.Description
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/order")
class OrderController {


    @PostMapping("/user/{userId}")
    @Description("상품 주문")
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