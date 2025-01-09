package kr.hhplus.be.server.domain.coupon

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
@Table(name = "coupon")
class CouponEntity(
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "capacity", nullable = false)
    var capacity: Int,
) : BaseEntity() {

    fun issue() {
        if (capacity == 0) throw IllegalArgumentException("남은 쿠폰 수량이 존재하지 않습니다.")
        capacity -= 1
    }
}