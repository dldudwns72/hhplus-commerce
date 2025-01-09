package kr.hhplus.be.server.application.coupon

import jakarta.transaction.Transactional
import kr.hhplus.be.server.domain.coupon.*
import kr.hhplus.be.server.domain.order.OrderEntity
import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val couponRepository: CouponRepository,
) {

    fun getCoupon(couponId: Long): CouponEntity {
        return couponRepository.findById(couponId)
            ?: throw IllegalArgumentException("coupon with ID $couponId not found")
    }
}