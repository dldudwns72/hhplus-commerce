package kr.hhplus.be.server.domain.order

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import kr.hhplus.be.server.domain.coupon.CouponEntity
import kr.hhplus.be.server.domain.user.UserEntity

@Entity
@Table(name = "orders")
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    val user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = true, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    val coupon: CouponEntity? = null,

    // 결제 총액?
    var totalAmount: Long = 0L,

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.RESERVED,

    // mappedBy = 매핑된 Entity 에서 선언된 필드명
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var orderProducts: MutableList<OrderProductEntity> = mutableListOf()
) : BaseEntity() {

    fun complete(totalAmount: Long) {
        this.totalAmount = totalAmount
        status = OrderStatus.COMPLETED
    }
}