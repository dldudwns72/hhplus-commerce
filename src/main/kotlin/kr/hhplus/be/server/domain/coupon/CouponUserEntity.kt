package kr.hhplus.be.server.domain.coupon

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import kr.hhplus.be.server.domain.order.OrderEntity
import kr.hhplus.be.server.domain.user.UserEntity

@Entity
@Table(name = "coupon_user")
class CouponUserEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true)
    var order: OrderEntity? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    var coupon: CouponEntity,
    var isUsed: Boolean = false,
) : BaseEntity() {
    fun use(order: OrderEntity) {
        this.isUsed = true
        this.order = order
    }
}


