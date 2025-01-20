package kr.hhplus.be.server.domain.coupon

import jakarta.transaction.Transactional
import kr.hhplus.be.server.application.coupon.CouponUserResult
import kr.hhplus.be.server.application.coupon.toCouponUserResult
import kr.hhplus.be.server.domain.user.UserEntity
import org.springframework.stereotype.Service

@Service
class CouponUserService(
    private val couponUserRepository: CouponUserRepository
) {

    fun create(user: UserEntity, coupon: CouponEntity): CouponUserEntity {
        return couponUserRepository.saveCouponUser(
            CouponUserEntity(
                user = user,
                coupon = coupon
            )
        )
    }

    @Transactional
    fun getCouponUsers(userId: Long): List<CouponUserResult> {
        return couponUserRepository.findCouponsByUserId(userId).map {
            it.toCouponUserResult()
        }
    }
}