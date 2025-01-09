package kr.hhplus.be.server.domain.user

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
@Table(name = "users")
class UserEntity(
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "balance", nullable = false)
    var balance: Long
) : BaseEntity() {

    fun addPoint(point: Long) {
        balance += point
    }

    fun usePoint(point: Long) {
        balance -= point
    }
}