package kr.hhplus.be.server.infra.coupon

import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.coupon.CouponRepository
import kr.hhplus.be.server.domain.coupon.IssuedCouponResult
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class CouponRepositoryImpl(
    private val couponJpaRepository: CouponJpaRepository,
    private val couponUserJpaRepository: CouponUserJpaRepository
) : CouponRepository {

    override fun findById(id: Long): CouponEntity? {
        return couponJpaRepository.findById(id).getOrNull()
    }

    override fun findCouponWithLock(id: Long): CouponEntity? {
        return couponJpaRepository.findCouponWithLock(id)
    }
}