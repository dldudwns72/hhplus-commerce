package kr.hhplus.be.server.controller.product

import kr.hhplus.be.server.controller.common.MultiResponse
import kr.hhplus.be.server.controller.common.PageResponse
import kr.hhplus.be.server.controller.common.SingleResponse
import kr.hhplus.be.server.controller.product.dto.PopularProductResponse
import kr.hhplus.be.server.controller.product.dto.ProductResponse
import kr.hhplus.be.server.controller.product.dto.toProductResponse
import kr.hhplus.be.server.domain.product.ProductService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/product")
class ProductController(
    private val productService: ProductService,
) : ProductSpecificationApi {

    @GetMapping
    override fun getProducts(
        pageable: Pageable
    ): ResponseEntity<PageResponse<ProductResponse>> {
        return ResponseEntity(
            PageResponse.execute {
                productService.getProducts(pageable)
            }, HttpStatus.OK
        )
    }

    @GetMapping("/{productId}")
    override fun getProduct(
        @PathVariable productId: Long,
    ): ResponseEntity<SingleResponse<ProductResponse>> {
        return ResponseEntity(
            SingleResponse.execute {
                productService.getProduct(productId).toProductResponse()
            }, HttpStatus.OK
        )
    }

    @GetMapping("/popular")
    override fun getPopularProduct(
        @RequestParam startDate: LocalDateTime,
        @RequestParam endDate: LocalDateTime
    ): ResponseEntity<MultiResponse<PopularProductResponse>> {
        return ResponseEntity(
            MultiResponse.execute {
                productService.getPopularProduct(startDate, endDate)
            }, HttpStatus.OK
        )
    }
}