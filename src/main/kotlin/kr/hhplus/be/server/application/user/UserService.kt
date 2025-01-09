package kr.hhplus.be.server.application.user

import kr.hhplus.be.server.application.order.OrderService
import kr.hhplus.be.server.controller.coupon.dto.CouponRequest
import kr.hhplus.be.server.controller.user.dto.*
import kr.hhplus.be.server.domain.coupon.CouponRepository
import kr.hhplus.be.server.domain.coupon.toUserCouponResponse
import kr.hhplus.be.server.domain.order.OrderProductRepository
import kr.hhplus.be.server.domain.order.OrderProductResult
import kr.hhplus.be.server.domain.order.OrderRepository
import kr.hhplus.be.server.domain.user.UserRepository
import kr.hhplus.be.server.domain.user.toUsers
import kr.hhplus.be.server.infra.order.OrderJpaRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository,
    private val orderProductRepository: OrderProductRepository,
    private val couponRepository: CouponRepository
) {

    fun chargePoint(id: Long, point: Long): UserResponse {
        val user = userRepository.findById(id) ?: throw IllegalStateException("유저를 찾을 수 없습니다.")
        user.addPoint(point)
        userRepository.saveUserBalance2(id, user.balance)
        return user.toResponse()
    }

    fun usePoint(id: Long, point: Long): UserResponse {
        val user = userRepository.findById(id) ?: throw IllegalStateException("유저를 찾을 수 없습니다.")
        user.usePoint(point)
        userRepository.saveUserBalance2(id, user.balance)
        return user.toResponse()
    }

    fun getUser(id: Long): UserResponse {
        val user = userRepository.findById(id) ?: throw IllegalStateException("유저를 찾을 수 없습니다.")
        return user.toResponse()
    }

    fun getCoupons(id: Long): List<UserCouponResponse> {
        return couponRepository.findCouponByUserId(userId = id).map { it.toUserCouponResponse() }
    }

    fun getOrders(userId: Long): List<OrderProductResult> {
        return orderProductRepository.findByUserId(userId)
    }
}