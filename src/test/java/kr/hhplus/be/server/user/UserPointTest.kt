package kr.hhplus.be.server.user

import kr.hhplus.be.server.application.user.UserService
import kr.hhplus.be.server.controller.user.dto.UserOrderResponse
import kr.hhplus.be.server.domain.coupon.CouponRepository
import kr.hhplus.be.server.domain.order.OrderRepository
import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.domain.user.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserPointTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var orderRepository: OrderRepository

    @Mock
    private lateinit var couponRepository: CouponRepository

    @InjectMocks
    private lateinit var userService: UserService


    @Test
    @DisplayName("유저 잔액 충전 성공")
    fun charge() {
        // given
        val point = 100L
        val userId = 1L
        val mockUser = UserEntity(
            name = "lee",
            balance = 100L
        )

        // when
        `when`(userRepository.findById(userId)).thenReturn(mockUser)
        val result = userService.chargePoint(userId, point)

        // then
        Assertions.assertEquals(result.balance, 200L)
    }

    @Test
    @DisplayName("유저 포인트 사용 [결제]")
    fun pay() {
        // given
        val point = 50L
        val userId = 1L
        val mockUser = UserEntity(
            name = "lee",
            balance = 100L
        )

        // when
        `when`(userRepository.findById(userId)).thenReturn(mockUser)
        val result = userService.usePoint(userId, point)

        // then
        Assertions.assertEquals(result.balance, 50L)
    }

    @Test
    @DisplayName("유저 잔액 조회")
    fun getUser() {
        // given
        val userId = 1L

        // when
        val mockUser = UserEntity(
            name = "lee",
            balance = 100L
        )
        `when`(userRepository.findById(userId)).thenReturn(mockUser)

        // then
        val result = userService.getUser(userId)
        Assertions.assertEquals(result.balance, 100L)
    }


}