package kr.hhplus.be.server.domain.payment

interface PaymentRepository {
    fun save(payment: PaymentEntity): PaymentEntity
}