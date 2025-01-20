package kr.hhplus.be.server.domain.payment

import kr.hhplus.be.server.domain.order.OrderEntity
import kr.hhplus.be.server.infra.DataPlatform
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository
) {

    fun pay(order: OrderEntity, amount: Long): PaymentEntity {
        return paymentRepository.save(PaymentEntity(order = order, amount = amount))
    }

    fun isSuccess(payment: PaymentEntity): Boolean {
        return DataPlatform.send(payment)
    }

}