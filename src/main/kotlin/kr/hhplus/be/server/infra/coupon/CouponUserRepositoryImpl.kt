package kr.hhplus.be.server.infra.coupon

import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import kr.hhplus.be.server.domain.coupon.CouponUserRepository
import org.springframework.stereotype.Repository

@Repository
class CouponUserRepositoryImpl(
    private val couponUserJpaRepository: CouponUserJpaRepository,
) : CouponUserRepository {
    override fun saveCouponUser(couponUser: CouponUserEntity): CouponUserEntity {
        return couponUserJpaRepository.save(couponUser)
    }

    override fun findCouponUserById(userId: Long): List<CouponUserEntity> {
        return couponUserJpaRepository.findCouponUserByUserId(userId)
    }
}