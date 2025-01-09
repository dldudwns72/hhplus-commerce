package kr.hhplus.be.server.infra.coupon

import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.coupon.CouponRepository
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import kr.hhplus.be.server.domain.coupon.IssuedCouponResult
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class CouponRepositoryImpl(
    private val couponJpaRepository: CouponJpaRepository,
    private val couponUserJpaRepository: CouponUserJpaRepository
) : CouponRepository {

    override fun findCouponByCouponIdAndUserId(couponId: Long, userId: Long): IssuedCouponResult? {
        return couponUserJpaRepository.findIssuedCouponResultByCouponIdAndUserId(couponId, userId)
    }

    override fun findCouponByUserId(userId: Long): List<IssuedCouponResult> {
        return couponUserJpaRepository.findIssuedCouponResultByUserId(userId)
    }

    override fun findById(id: Long): CouponEntity? {
        return couponJpaRepository.findById(id).getOrNull()
    }

    override fun saveCouponUser(couponId: Long, userId: Long) {
        val couponUser = CouponUserEntity(
            userId = userId,
            couponId = couponId
        )
        couponUserJpaRepository.save(couponUser)
    }
}