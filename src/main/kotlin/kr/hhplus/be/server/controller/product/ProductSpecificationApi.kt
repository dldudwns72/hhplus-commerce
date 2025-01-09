package kr.hhplus.be.server.controller.product

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.controller.common.PageResponse
import kr.hhplus.be.server.controller.common.SingleResponse
import kr.hhplus.be.server.controller.coupon.dto.response.CouponResponse
import kr.hhplus.be.server.controller.order.dto.request.OrderRequest
import kr.hhplus.be.server.controller.order.dto.response.OrderResponse
import kr.hhplus.be.server.controller.product.dto.PopularProductResponse
import kr.hhplus.be.server.controller.product.dto.ProductResponse
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@Tag(name = "상품 API")
interface ProductSpecificationApi {

    @Operation(summary = "상품 목록 조회 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "목록 조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ProductResponse::class))
                    )
                ]
            )
        ],
    )
    @GetMapping
    fun getProducts(
        pageable: Pageable
    ): ResponseEntity<PageResponse<ProductResponse>>

    @Operation(summary = "상품 상세 조회 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "목록 상세 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ProductResponse::class))
                    )
                ]
            )
        ],
    )
    fun getProduct(
        @PathVariable productId: Long,
    ): ResponseEntity<SingleResponse<ProductResponse>>

    @Operation(summary = "상위 인기 상품 조회 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "인기 상품 조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = PopularProductResponse::class))
                    )
                ]
            )
        ],
    )
    fun getPopularProduct(
        @RequestParam startDate: LocalDateTime,
        @RequestParam endDate: LocalDateTime,
        pageable: Pageable,
    ): ResponseEntity<PageResponse<PopularProductResponse>>
}