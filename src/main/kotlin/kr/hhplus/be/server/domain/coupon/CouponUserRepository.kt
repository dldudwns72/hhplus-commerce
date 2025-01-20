package kr.hhplus.be.server.domain.coupon

interface CouponUserRepository {
    fun saveCouponUser(couponUser: CouponUserEntity): CouponUserEntity
    fun findCouponUserById(id: Long): CouponUserEntity?
    fun findCouponsByUserId(userId: Long): List<CouponUserEntity>
    fun findCouponsByCouponId(couponId: Long): List<CouponUserEntity>
    fun findAll(): List<CouponUserEntity>
}