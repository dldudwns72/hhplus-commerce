package kr.hhplus.be.server.domain.user

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun chargePoint(id: Long, point: Long): UserEntity {
        val user = userRepository.findById(id) ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")
        user.addPoint(point)
        userRepository.updateUserBalance(id, user.balance)
        return user
    }

    @Transactional
    fun usePoint(id: Long, point: Long): UserEntity {
        val user = userRepository.findById(id) ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")
        user.usePoint(point)
        userRepository.updateUserBalance(id, user.balance)
        return user
    }

    fun getUserById(id: Long): UserEntity {
        return userRepository.findById(id) ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")
    }
}