package kr.hhplus.be.server.controller.coupon

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.controller.common.ApiErrorResponse
import kr.hhplus.be.server.controller.coupon.dto.CouponRequest
import kr.hhplus.be.server.controller.coupon.dto.CouponResponse
import kr.hhplus.be.server.controller.user.dto.UserResponse
import org.springframework.context.annotation.Description
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/coupon")
@Tag(name = "쿠폰 API")
class CouponController {

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
            ),
            ApiResponse(
                responseCode = "400", description = "쿠폰 생성 실패", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ApiErrorResponse::class))
                    )
                ]
            )
        ]
    )
    @PostMapping
    fun coupon(@RequestBody request: CouponRequest): ResponseEntity<CouponResponse> {
        return ResponseEntity(HttpStatus.CREATED)
    }


    @Operation(summary = "유저 쿠폰 발급 성공")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "쿠폰 발급 성공", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = CouponResponse::class))
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400", description = "쿠폰 발급 실패", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = CouponResponse::class))
                    )
                ]
            )
        ]
    )
    @PostMapping("{couponId}/issue/{userId}")
    fun issueCoupon(
        @PathVariable couponId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<CouponResponse> {
        if (userId == 1L) { // 이미 발급 받은 유저
            throw IllegalStateException("이미 발급 받은 유저입니다.")
        }
        if (couponId == 1L) {
            throw IllegalStateException("쿠폰 수량이 소진되었습니다.")
        }
        return ResponseEntity(HttpStatus.CREATED)
    }


}