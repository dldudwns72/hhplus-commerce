package kr.hhplus.be.server.infra.coupon

import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import kr.hhplus.be.server.domain.coupon.CouponUserRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class CouponUserRepositoryImpl(
    private val couponUserJpaRepository: CouponUserJpaRepository,
) : CouponUserRepository {
    override fun saveCouponUser(couponUser: CouponUserEntity): CouponUserEntity {
        return couponUserJpaRepository.save(couponUser)
    }

    override fun findCouponUserById(id: Long): CouponUserEntity? {
        return couponUserJpaRepository.findById(id).getOrNull()
    }

    override fun findCouponsByUserId(userId: Long): List<CouponUserEntity> {
        return couponUserJpaRepository.findCouponByUserId(userId)
    }

    override fun findCouponsByCouponId(couponId: Long): List<CouponUserEntity> {
        return couponUserJpaRepository.findCouponByUserId(couponId)
    }

    override fun findAll(): List<CouponUserEntity> {
        return couponUserJpaRepository.findAll()
    }
}