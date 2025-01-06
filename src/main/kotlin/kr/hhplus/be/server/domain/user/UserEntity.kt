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
    val name: String,
    @Column(name = "balance", nullable = false)
    val balance: Long
)