package kr.hhplus.be.server.domain.coupon

interface CouponUserRepository {
    fun saveCouponUser(couponUser: CouponUserEntity):CouponUserEntity
    fun findCouponUserById(userId: Long): List<CouponUserEntity>
}