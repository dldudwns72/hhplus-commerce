package kr.hhplus.be.server.controller.product

import kr.hhplus.be.server.application.product.ProductService
import kr.hhplus.be.server.controller.product.dto.PopularProductResponse
import kr.hhplus.be.server.controller.product.dto.ProductResponse
import org.springframework.context.annotation.Description
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("v1/product/")
class ProductController(
) {

    @GetMapping
    @Description("상품 목록 조회")
    fun getProducts(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "20") size: Int,
    ): ResponseEntity<List<ProductResponse>> {
        val products = listOf(
            ProductResponse(id = 1, title = "Product 1", inventory = 10, price = 100),
            ProductResponse(id = 2, title = "Product 2", inventory = 20, price = 300),
            ProductResponse(id = 3, title = "Product 3", inventory = 30, price = 200)
        )
        return ResponseEntity(products, HttpStatus.OK)
    }

    @GetMapping("/{productId}")
    @Description("상품 상세 조회")
    fun getProduct(
        @PathVariable productId: Long,
    ): ResponseEntity<ProductResponse> {
        val response = ProductResponse(id = 3, title = "Product 3", inventory = 30, price = 200)
        return ResponseEntity(response, HttpStatus.OK)
    }

    private val defaultStartDate: LocalDate = LocalDate.now().minusDays(3) // 3일 전
    private val defaultEndDate: LocalDate = LocalDate.now() // 현재 날짜

    @GetMapping("/popular")
    @Description("인기 상품 상위 5개 조회")
    fun getPopularProduct(
        @RequestParam(value = "startDate", defaultValue = "") startDate: String?,
        @RequestParam(value = "endDate", defaultValue = "") endDate: String?,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "5") size: Int
    ): ResponseEntity<List<PopularProductResponse>> {
        val parsedStartDate = if (startDate.isNullOrBlank()) defaultStartDate else LocalDate.parse(startDate)
        val parsedEndDate = if (endDate.isNullOrBlank()) defaultEndDate else LocalDate.parse(endDate)
        val popularProductResponse = listOf(
            PopularProductResponse(id = 1, title = "Product 1", inventory = 10, price = 100, rank = 1, orderCount = 10),
            PopularProductResponse(id = 2, title = "Product 2", inventory = 20, price = 300, rank = 2, orderCount = 9),
            PopularProductResponse(id = 3, title = "Product 3", inventory = 30, price = 200, rank = 3, orderCount = 8),
            PopularProductResponse(id = 4, title = "Product 5", inventory = 40, price = 200, rank = 4, orderCount = 4),
            PopularProductResponse(id = 5, title = "Product 4", inventory = 50, price = 200, rank = 5, orderCount = 1)
        )
        return ResponseEntity(popularProductResponse, HttpStatus.OK)
    }
}