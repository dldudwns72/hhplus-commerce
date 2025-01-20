package kr.hhplus.be.server.domain.coupon

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import kr.hhplus.be.server.domain.order.OrderEntity
import kr.hhplus.be.server.domain.user.UserEntity

@Entity
@Table(name = "coupon_user")
class CouponUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    var coupon: CouponEntity,
    var isUsed: Boolean = false,
) : BaseEntity() {

    fun use() {
        this.isUsed = true
    }

    fun usedCheck() {
        if(isUsed) throw IllegalArgumentException("이미 사용된 쿠폰입니다. couponId: ${coupon.id}")
    }
}


