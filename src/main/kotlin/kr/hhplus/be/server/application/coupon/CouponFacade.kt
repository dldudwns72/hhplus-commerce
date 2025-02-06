package kr.hhplus.be.server.application.coupon

import jakarta.transaction.Transactional
import kr.hhplus.be.server.domain.coupon.CouponUserService
import kr.hhplus.be.server.domain.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CouponFacade(
    private val userService: UserService,
    private val couponService: CouponService,
    private val couponUserService: CouponUserService,
    private val couponRedisService: CouponRedisService
) {

    @Transactional
    fun issue(couponId: Long, userId: Long): CouponUserResult {
        val timestamp = System.currentTimeMillis().toDouble()
        val user = userService.getUserById(userId)
        val coupon = couponService.getCoupon(couponId)

        couponRedisService.issuedCouponUserVerify(coupon.id.toString(), user.id.toString())
        couponRedisService.soldOutCouponUserVerify(coupon.id.toString(), coupon.maxCapacity)

        couponRedisService.issue(coupon.id.toString(), userId.toString(), timestamp)
        coupon.issue() // dirty-checking

        return couponUserService.create(user, coupon).toCouponUserResult()
    }
}