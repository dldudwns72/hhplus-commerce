package kr.hhplus.be.server.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.application.user.UserService
import kr.hhplus.be.server.controller.user.dto.*
import kr.hhplus.be.server.domain.order.OrderProductResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "유저 관련 API")
class UserController(
    private val userService: UserService
) {

    @Operation(summary = "유저 잔액 충전 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "충전 성공", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = UserResponse::class))
                    )
                ]
            )
        ]
    )
    @PostMapping("/{userId}/balance")
    fun chargeBalance(
        @PathVariable userId: Long,
        @RequestBody request: BalanceRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity(
            userService.chargePoint(userId, request.point),
            HttpStatus.OK
        )
    }


    @Operation(summary = "유저 잔액 조회 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "조회 성공", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = UserResponse::class))
                    )
                ]
            )
        ]
    )
    @GetMapping("/{userId}")
    fun gatUserBalance(
        @PathVariable userId: Long
    ): ResponseEntity<UserResponse> {
        return ResponseEntity(userService.getUser(userId), HttpStatus.OK)
    }

    @Operation(summary = "주문 목록 조회")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "주문 조회 성공", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = UserOrderResponse::class))
                    )
                ]
            )
        ]
    )
    @GetMapping("{userId}/orders")
    fun getOrders(@PathVariable userId: Long): ResponseEntity<List<OrderProductResult>> {
        return ResponseEntity(userService.getOrders(userId), HttpStatus.OK)
    }

    @Operation(summary = "발급된 유저 쿠폰 조회")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "쿠폰 조회 성공", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = UserCouponResponse::class))
                    )
                ]
            )
        ]
    )
    @GetMapping("{userId}/coupons")
    fun getCoupon(
        @PathVariable userId: Long
    ): ResponseEntity<List<UserCouponResponse>> {
        return ResponseEntity(userService.getCoupons(userId), HttpStatus.CREATED)
    }

}

