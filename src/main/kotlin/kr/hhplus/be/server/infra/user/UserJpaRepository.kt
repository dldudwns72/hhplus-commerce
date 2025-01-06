package kr.hhplus.be.server.infra.user

import kr.hhplus.be.server.domain.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    // 유저 잔액 충전
//    @Query("UPDATE userEntity set balance = :totalBalance where id = :userId")
//    @Modifying
//    fun saveUserBalance(
//        @Param("userId") userId: Long,
//        @Param("totalBalance") totalBalance: Long
//    )

    // 유저 잔액 조회
    fun findBalanceById(id: Long): Int


}