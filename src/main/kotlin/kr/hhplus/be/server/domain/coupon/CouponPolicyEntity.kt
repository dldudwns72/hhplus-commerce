package kr.hhplus.be.server.domain.coupon

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
@Table(name = "coupon_policy")
class CouponPolicyEntity(
    @Column(name = "coupon_id", nullable = true)
    val couponId: Long,
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = true)
    val discountType: CouponDiscountType,
    @Column(name = "discount_value", nullable = true)
    val discountValue: Long
) : BaseEntity() {
}