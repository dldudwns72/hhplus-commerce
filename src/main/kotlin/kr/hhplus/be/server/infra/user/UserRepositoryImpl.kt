package kr.hhplus.be.server.infra.user

import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.domain.user.UserRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

//    override fun saveUserBalance(id: Long, totalBalance: Long): UserEntity {
//        return userJpaRepository.saveUserBalance(id, totalBalance)
//    }

    override fun findBalanceById(id: Long): Int {
        return userJpaRepository.findBalanceById(id)
    }

    override fun findById(id: Long): UserEntity? {
        return userJpaRepository.findById(id).getOrNull()
    }

}