package kr.hhplus.be.server.controller.coupon

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.controller.common.MultiResponse
import kr.hhplus.be.server.controller.common.SingleResponse
import kr.hhplus.be.server.controller.coupon.dto.response.CouponResponse
import kr.hhplus.be.server.controller.user.dto.response.CouponUserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "쿠폰 유저 API")
interface CouponUserSpecificationApi {

    @Operation(summary = "유저가 발급한 쿠폰 조회 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "유저가 발급 받은 쿠폰 조회 ", content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = CouponResponse::class))
                    )
                ]
            )
        ]
    )
    fun getCouponUser(
        @PathVariable userId: Long
    ): ResponseEntity<MultiResponse<CouponUserResponse>>
}