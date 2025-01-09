package kr.hhplus.be.server.infra.user

import kr.hhplus.be.server.domain.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    // 유저 잔액 충전
    // 동시성 체크
    // 플러스 연산..? balance + amount
    @Query("UPDATE UserEntity set balance = :totalBalance where id = :userId")
    @Modifying
    fun saveUserBalance(
        @Param("userId") userId: Long,
        @Param("totalBalance") totalBalance: Long
    )

    // 유저 잔액 조회
    fun findBalanceById(id: Long): Int


}