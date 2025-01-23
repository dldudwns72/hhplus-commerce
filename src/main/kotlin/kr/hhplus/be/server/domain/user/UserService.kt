package kr.hhplus.be.server.domain.user

import jakarta.transaction.Transactional
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun chargePoint(id: Long, point: Long, maxRetry: Int = 3): UserEntity {
        val user = userRepository.findById(id) ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")
        var retryCount = 0

        while (retryCount < maxRetry) {
            try {
                user.addPoint(point)
                return user
            } catch (olfe: ObjectOptimisticLockingFailureException) {
                retryCount++
                if (retryCount >= maxRetry) {
                    throw IllegalStateException("Failed to charge points after retry: $retryCount")
                }
            }
        }
        throw IllegalStateException("Unexpected error in chargePoint function")
    }

    @Transactional
    fun usePoint(id: Long, point: Long, maxRetry: Int = 3): UserEntity {
        val user = userRepository.findById(id) ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")
        var retryCount = 0

        while (retryCount < maxRetry) {
            try {
                user.usePoint(point)
                return user
            } catch (olfe: OptimisticLockingFailureException) {
                retryCount++
                if (retryCount >= maxRetry) {
                    throw IllegalStateException("Failed to charge points after retry: $retryCount")
                }
            }
        }
        throw IllegalStateException("Unexpected error")
    }

    fun getUserById(id: Long): UserEntity {
        return userRepository.findById(id) ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")
    }
}