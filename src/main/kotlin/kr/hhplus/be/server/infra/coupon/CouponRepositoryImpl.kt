package kr.hhplus.be.server.infra.coupon

import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.coupon.CouponRepository
import org.springframework.stereotype.Repository

@Repository
class CouponRepositoryImpl(
    private val couponJpaRepository: CouponJpaRepository
) : CouponRepository {

    override fun findCouponWithLock(id: Long): CouponEntity? {
        return couponJpaRepository.findCouponWithLock(id)
    }

    override fun save(coupon: CouponEntity): CouponEntity {
        return couponJpaRepository.save(coupon)

    }
}