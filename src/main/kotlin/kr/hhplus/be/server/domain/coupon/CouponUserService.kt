package kr.hhplus.be.server.domain.coupon

import jakarta.transaction.Transactional
import kr.hhplus.be.server.application.coupon.CouponUserResult
import kr.hhplus.be.server.application.coupon.toCouponUserResult
import kr.hhplus.be.server.controller.user.dto.response.CouponUserResponse
import kr.hhplus.be.server.controller.user.dto.response.toResponse
import kr.hhplus.be.server.domain.user.UserEntity
import org.springframework.stereotype.Service

@Service
class CouponUserService(
    private val couponUserRepository: CouponUserRepository
) {

    fun create(user: UserEntity, coupon: CouponEntity): CouponUserResult {
        return couponUserRepository.saveCouponUser(
            CouponUserEntity(
                user = user,
                coupon = coupon
            )
        ).toCouponUserResult()
    }

    @Transactional
    fun getCouponUsers(userId: Long): List<CouponUserEntity> {
        return couponUserRepository.findCouponUserById(userId)
    }
}