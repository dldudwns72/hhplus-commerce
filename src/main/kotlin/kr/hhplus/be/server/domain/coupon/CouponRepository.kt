package kr.hhplus.be.server.domain.coupon

interface CouponRepository {
    fun findById(id: Long): CouponEntity?
    fun findCouponWithLock(id: Long): CouponEntity?
}