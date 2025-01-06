package kr.hhplus.be.server.controller.product

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import kr.hhplus.be.server.application.product.ProductService
import kr.hhplus.be.server.controller.product.dto.PopularProductResponse
import kr.hhplus.be.server.controller.product.dto.ProductResponse
import kr.hhplus.be.server.controller.user.dto.UserResponse
import org.springframework.context.annotation.Description
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("v1/product/")
@Tag(name = "상품 API")
class ProductController(
) {

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
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "20") size: Int,
    ): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity(HttpStatus.OK)
    }

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
    @GetMapping("/{productId}")
    fun getProduct(
        @PathVariable productId: Long,
    ): ResponseEntity<ProductResponse> {
        return ResponseEntity(HttpStatus.OK)
    }

    private val defaultStartDate: LocalDate = LocalDate.now().minusDays(3) // 3일 전
    private val defaultEndDate: LocalDate = LocalDate.now() // 현재 날짜

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
    @GetMapping("/popular")
    fun getPopularProduct(
        @RequestParam(value = "startDate", defaultValue = "") startDate: String?,
        @RequestParam(value = "endDate", defaultValue = "") endDate: String?,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "5") size: Int
    ): ResponseEntity<List<PopularProductResponse>> {
        return ResponseEntity(listOf(), HttpStatus.OK)
    }
}