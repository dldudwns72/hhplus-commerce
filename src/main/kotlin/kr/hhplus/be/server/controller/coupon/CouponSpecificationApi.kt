package kr.hhplus.be.server.controller.coupon

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.controller.common.SingleResponse
import kr.hhplus.be.server.controller.coupon.dto.response.CouponResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "쿠폰 API")
interface CouponSpecificationApi {

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
    fun issueCoupon(
        @PathVariable couponId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<SingleResponse<IssuedCouponResponse>>
}