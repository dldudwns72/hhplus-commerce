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
    private val couponUserRepository: CouponUserRepository,
) {

    fun getCoupon(couponId: Long): CouponEntity {
        return couponRepository.findCouponWithLock(couponId)
            ?: throw IllegalArgumentException("coupon with ID $couponId not found")
    }

    // 이것도 CouponUser 서비스롤 분리해야 할까?
    fun getCouponsUser(couponUserId: Long): CouponUserEntity? {
        val couponUser = couponUserRepository.findCouponUserById(couponUserId)
            ?: throw IllegalArgumentException("coupon user with ID $couponUserId not found")
        return couponUser.also { it.usedCheck() }
    }
}