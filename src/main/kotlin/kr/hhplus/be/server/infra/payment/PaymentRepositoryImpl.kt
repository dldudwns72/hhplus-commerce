package kr.hhplus.be.server.infra.payment

import kr.hhplus.be.server.domain.payment.PaymentEntity
import kr.hhplus.be.server.domain.payment.PaymentRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository
) : PaymentRepository {
    override fun save(payment: PaymentEntity): PaymentEntity {
        return paymentJpaRepository.save(payment)
    }

}