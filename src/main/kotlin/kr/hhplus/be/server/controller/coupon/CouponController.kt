package kr.hhplus.be.server.controller.coupon

import kr.hhplus.be.server.controller.coupon.dto.CouponRequest
import kr.hhplus.be.server.controller.coupon.dto.CouponResponse
import org.springframework.context.annotation.Description
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/coupon")
class CouponController {

    @PostMapping
    @Description(value = "쿠폰 생성")
    fun coupon(@RequestBody request: CouponRequest): ResponseEntity<CouponResponse> {
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PostMapping("{couponId}/issue/{userId}")
    @Description(value = "유저 쿠폰 발급")
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