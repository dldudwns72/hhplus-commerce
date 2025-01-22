package kr.hhplus.be.server.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.controller.common.SingleResponse
import kr.hhplus.be.server.controller.user.dto.request.BalanceRequest
import kr.hhplus.be.server.controller.user.dto.response.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "유저 API")
interface UserSpecificationApi {
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
    fun chargeBalance(
        userId: Long,
        @RequestBody request: BalanceRequest
    ): ResponseEntity<SingleResponse<UserResponse>>


    @Operation(summary = "유저 잔액 조회 ")
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
    fun gatUserBalance(
        userId: Long
    ): ResponseEntity<SingleResponse<UserResponse>>
}