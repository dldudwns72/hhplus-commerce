package kr.hhplus.be.server.controller.user

import kr.hhplus.be.server.application.user.UserService
import kr.hhplus.be.server.controller.coupon.dto.CouponRequest
import kr.hhplus.be.server.controller.coupon.dto.CouponResponse
import kr.hhplus.be.server.controller.order.dto.OrderResponse
import kr.hhplus.be.server.controller.user.dto.*
import org.springframework.context.annotation.Description
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/user")
class UserController(
) {

    @PostMapping("/{userId}/balance")
    @Description("유저 잔액 충전")
    fun chargeBalance(
        @PathVariable userId: Long?,
        @RequestBody request: BalanceRequest
    ): ResponseEntity<UserResponse> {
        if (request.point < 0) {
            throw Exception("충전 금액은 0 이상 이여야 합니다.")
        }

        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/{userId}/balance")
    @Description("유저 잔액 조회")
    fun gatUserBalance(
        @PathVariable userId: Long
    ): ResponseEntity<UserBalanceResponse> {
        val response = UserBalanceResponse(userId = userId, name = "잔액", balance = 100)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("{userId}/orders")
    @Description("유저 주문 목록 조회")
    fun getOrders(@PathVariable userId: Long): ResponseEntity<List<UserOrderResponse>> {
        val response = listOf(
            UserOrderResponse(userId = userId, orderId = 1),
            UserOrderResponse(userId = userId, orderId = 2)
        )
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("{userId}/coupons")
    @Description(value = "유저 쿠폰 조회")
    fun getCoupon(
        @RequestBody request: CouponRequest,
        @PathVariable userId: Long
    ): ResponseEntity<UserCouponResponse> {
        val response = listOf(
            UserCouponResponse(id = userId, couponId = 1),
            UserCouponResponse(id = userId, couponId = 2)
        )
        return ResponseEntity(HttpStatus.CREATED)
    }

}

