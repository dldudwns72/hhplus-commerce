package kr.hhplus.be.server.domain.coupon

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
@Table(name = "coupon")
class CouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "capacity", nullable = false)
    var capacity: Int,
    @Column(name = "max_capacity", nullable = false)
    var maxCapacity: Int,
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    val discountType: CouponDiscountType,
    @Column(name = "discount_value", nullable = false)
    val discountValue: Int,
) : BaseEntity() {

    fun issue() {
        if (capacity == 0) throw IllegalArgumentException("남은 쿠폰 수량이 존재하지 않습니다.")
        capacity -= 1
    }
}