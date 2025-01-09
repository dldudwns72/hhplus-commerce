package kr.hhplus.be.server.domain.coupon

interface CouponRepository {
    fun findCouponByCouponIdAndUserId(couponId: Long, userId: Long): IssuedCouponResult?
    fun findCouponByUserId(userId: Long): List<IssuedCouponResult>
    fun findById(id: Long): CouponEntity?
    fun saveCouponUser(couponId: Long, userId: Long)
}