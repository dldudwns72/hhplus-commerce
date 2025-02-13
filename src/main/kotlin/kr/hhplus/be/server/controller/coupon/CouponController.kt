package kr.hhplus.be.server.controller.coupon

import kr.hhplus.be.server.application.coupon.CouponFacade
import kr.hhplus.be.server.controller.common.SingleResponse
import kr.hhplus.be.server.controller.user.dto.response.CouponUserResponse
import kr.hhplus.be.server.controller.user.dto.response.toResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/coupon")
class CouponController(
    private val couponFacade: CouponFacade
) : CouponSpecificationApi {

    @PostMapping("/{couponId}/users")
    override fun issueCoupon(
        @PathVariable couponId: Long,
        @RequestHeader("user-id") userId: Long
    ): ResponseEntity<SingleResponse<CouponUserResponse>> {
        return ResponseEntity(
            SingleResponse.execute {
                couponFacade.issue(couponId, userId).toResponse()
            },
            HttpStatus.OK
        )
    }
}


