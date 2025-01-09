package kr.hhplus.be.server.domain.coupon

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
@Table(name = "coupon_user")
class CouponUserEntity(
    val userId: Long,
    val couponId: Long,
    var orderId: Long? = null,
    var isUsed: Boolean = false,
) : BaseEntity() {

    fun use(orderId: Long?) {
        this.orderId = orderId
        this.isUsed = true
    }
}