package kr.hhplus.be.server.controller.coupon

import kr.hhplus.be.server.controller.common.MultiResponse
import kr.hhplus.be.server.controller.user.dto.response.CouponUserResponse
import kr.hhplus.be.server.controller.user.dto.response.toResponse
import kr.hhplus.be.server.domain.coupon.CouponUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/coupon")
class CouponUserController(
    private val couponUserService: CouponUserService,
) : CouponUserSpecificationApi {

    @GetMapping("/user/{userId}")
    override fun getCouponUser(
        @PathVariable userId: Long
    ): ResponseEntity<MultiResponse<CouponUserResponse>> {
        return ResponseEntity(
            MultiResponse.execute {
                couponUserService.getCouponUsers(userId).map { it.toResponse() }
            }, HttpStatus.OK
        )
    }


}