package kr.hhplus.be.server.application.user

import kr.hhplus.be.server.controller.user.dto.UserResponse
import kr.hhplus.be.server.controller.user.dto.toResponse
import kr.hhplus.be.server.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
     private val userRepository: UserRepository
) {

    fun saveBalance(id: Long, point: Long): UserResponse {
//        val user = userRepository.findById(id)
//            ?: throw IllegalStateException("유저 정보를 찾을 수 없습니다.")
//        // userRepository.saveUserBalance(user.id, user.balance + point)
//        return user.toResponse()
        return UserResponse(1,",",3)
    }
}