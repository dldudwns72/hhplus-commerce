package kr.hhplus.be.server.domain.user

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "balance", nullable = false)
    var balance: Long
    // 연관관계 설정
) : BaseEntity() {

    fun addPoint(point: Long) {
        balance += point
    }

    fun usePoint(point: Long) {
        balance -= point
    }
}