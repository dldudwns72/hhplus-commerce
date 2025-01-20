package kr.hhplus.be.server.domain.coupon

interface CouponRepository {
    fun findCouponWithLock(id: Long): CouponEntity?
    fun save(coupon: CouponEntity): CouponEntity
}