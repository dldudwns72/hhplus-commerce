package kr.hhplus.be.server.controller.coupon

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.application.coupon.CouponService
import kr.hhplus.be.server.controller.coupon.dto.CouponRequest
import kr.hhplus.be.server.controller.coupon.dto.CouponResponse
import kr.hhplus.be.server.controller.user.dto.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/coupon")
@Tag(name = "쿠폰 API")
class CouponController(
    private val couponService: CouponService
) {

    @Operation(summary = "쿠폰 생성 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "조회 생성 성공", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = UserResponse::class))
                    )
                ]
            )
        ]
    )
    @PostMapping
    fun coupon(@RequestBody request: CouponRequest): ResponseEntity<CouponResponse> {
        return ResponseEntity(HttpStatus.CREATED)
    }


    @Operation(summary = "유저 쿠폰 발급 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "쿠폰 발급 성공", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = CouponResponse::class))
                    )
                ]
            )
        ]
    )
    @PostMapping("{couponId}/users/{userId}") // issue라는 동사를 uri에 넣으면 restful 하지 않다..?
    fun issueCoupon(
        @PathVariable couponId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<CouponResponse> {
        return ResponseEntity(
            couponService.issue(couponId, userId),
            HttpStatus.CREATED
        )
    }
}


