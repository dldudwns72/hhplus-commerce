package kr.hhplus.be.server.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.application.user.UserService
import kr.hhplus.be.server.controller.common.ApiErrorResponse
import kr.hhplus.be.server.controller.common.SingleResponse
import kr.hhplus.be.server.controller.coupon.dto.CouponRequest
import kr.hhplus.be.server.controller.user.dto.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 잔액 충전 / 조회 API
 * 유저 잔액 조회
 * 유저 주문 목록 조회
 * 유저 쿠폰 조회
 */
@RestController
@RequestMapping("v1/user")
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
            ),
            ApiResponse(
                responseCode = "400", description = "충전 실패", content = [
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
    ): SingleResponse<UserResponse> {
        return SingleResponse.execute {
            userService.saveBalance(userId, request.point)
        }
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
            ),
            ApiResponse(
                responseCode = "400", description = "조회 실패", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ApiErrorResponse::class))
                    )
                ]
            )
        ]
    )
    @GetMapping("/{userId}/balance")
    fun gatUserBalance(
        @PathVariable userId: Long
    ): ResponseEntity<UserResponse> {
        return ResponseEntity(HttpStatus.OK)
    }

    @Operation(summary = "주문 목록 조회")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "조회 성공", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = UserResponse::class))
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400", description = "조회 실패", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ApiErrorResponse::class))
                    )
                ]
            )
        ]
    )
    @GetMapping("{userId}/orders")
    fun getOrders(@PathVariable userId: Long): ResponseEntity<List<UserOrderResponse>> {
        return ResponseEntity(HttpStatus.OK)
    }

    @Operation(summary = "발급된 유저 쿠폰 조회")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "조회 성공", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = UserResponse::class))
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400", description = "조회 실패", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ApiErrorResponse::class))
                    )
                ]
            )
        ]
    )
    @PostMapping("{userId}/coupons")
    fun getCoupon(
        @PathVariable userId: Long,
        @RequestBody request: CouponRequest
    ): ResponseEntity<UserCouponResponse> {
        return ResponseEntity(HttpStatus.CREATED)
    }

}

