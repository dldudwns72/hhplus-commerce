package kr.hhplus.be.server.domain.payment

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import kr.hhplus.be.server.domain.order.OrderEntity

@Entity
@Table(name = "payment")
class PaymentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    val order: OrderEntity,
    @Column(name = "amount", nullable = false)
    val amount: Long
) : BaseEntity()