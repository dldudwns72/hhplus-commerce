package kr.hhplus.be.server.domain.payment

import kr.hhplus.be.server.domain.order.*
import kr.hhplus.be.server.domain.order.event.OrderCanceledEvent
import kr.hhplus.be.server.domain.order.event.OrderCompletedEvent
import kr.hhplus.be.server.domain.order.event.OrderCreateEvent
import kr.hhplus.be.server.domain.user.UserRepository
import kr.hhplus.be.server.infra.DataPlatform
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository,
    private val eventPublisher: ApplicationEventPublisher
) {

    val logger = LoggerFactory.getLogger("payment-service")

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleOrderCreatedEvent(event: OrderCreateEvent) {
        val order = event.order
        logger.info("결제 진행 중 (주문 ID = ${order.id}, 금액 = ${order.totalAmount})")

        val payment = paymentRepository.save(PaymentEntity(order = order, amount = order.totalAmount))

        // 외부 결제 API 호출
        if (DataPlatform.send(payment)) {
            // 결제 성공 -> 상태 변경
            eventPublisher.publishEvent(PaymentCompletedEvent(order.id, order.user.id, order.totalAmount))
            logger.info("결제 완료: $order")
        } else {
            // 결제가 성공적으로 끝나지 않았다면 추가 이벤트를 발행할 필요 없음,
            logger.info("결제 실패 (주문 ID = ${order.id}")
        }
    }

    @Async
    @EventListener
    fun handlePaymentCompletedEvent(event: PaymentCompletedEvent) {
        logger.info("유저 포인트 차감 진행 중 (사용자 ID = ${event.userId}, 결제 금액 = ${event.amount})")
        val user =
            userRepository.findById(event.userId)
                ?: throw IllegalArgumentException("유저 정보를 찾을 수 없습니다. ${event.userId}")

        val order = orderRepository.findById(event.orderId)
            ?: throw IllegalArgumentException("주문 정보를 찾을 수 없습니다. ${event.orderId}")

        // 유저 포인트 차감
        try {
            user.usePoint(event.amount)
            userRepository.save(user)

            eventPublisher.publishEvent(OrderCompletedEvent(order))

            logger.info(" 유저 포인트 차감 완료 (사용자 ID = ${event.userId}, 차감: ${order.totalAmount})")
        } catch (e: Exception) {
            eventPublisher.publishEvent(OrderCanceledEvent(order.id, user.id, order.totalAmount))
            logger.info("유저 포인트 차감 실패 (사용자 ID = ${event.userId}, 차감 금액 = ${event.amount})")
        }
    }

}
