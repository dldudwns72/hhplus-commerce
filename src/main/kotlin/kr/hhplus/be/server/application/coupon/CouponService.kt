package kr.hhplus.be.server.application.coupon

import jakarta.transaction.Transactional
import kr.hhplus.be.server.controller.coupon.dto.CouponResponse
import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.coupon.CouponRepository
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val couponRepository: CouponRepository,
) {

    @Transactional
    fun issue(couponId: Long, userId: Long): CouponResponse {
        val coupon: CouponEntity = couponRepository.findById(couponId) ?: throw Exception("coupon not found")
        coupon.issue()
        couponRepository.saveCouponUser(couponId, userId)
        return CouponResponse(
            couponId = coupon.id,
            userId = userId
        )
    }
}