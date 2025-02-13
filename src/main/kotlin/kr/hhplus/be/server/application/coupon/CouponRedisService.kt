package kr.hhplus.be.server.application.coupon

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class CouponRedisService(
    private val redisTemplate: RedisTemplate<String, String>,
) {

    fun issuedCouponUserVerify(couponId: String, userId: String) {
        val score =  redisTemplate.opsForZSet().score(getKey(couponId), userId)

        if (score != null) {
            throw IllegalStateException("해당 쿠폰을 이미 발급 받았습니다.")
        }
    }

    fun soldOutCouponUserVerify(couponId: String, maxCapacity: Int) {
        val issuedCouponCount = redisTemplate.opsForZSet().zCard(getKey(couponId))
        if (issuedCouponCount != null && issuedCouponCount >= maxCapacity) {
            throw IllegalStateException("쿠폰 수량이 소진 되었습니다.")
        }
    }

    fun issue(couponId: String, value: String, score: Double) {
        redisTemplate.opsForZSet().add(getKey(couponId), value, score)
    }

    fun getKey(couponId: String): String = "coupon:$couponId"

}