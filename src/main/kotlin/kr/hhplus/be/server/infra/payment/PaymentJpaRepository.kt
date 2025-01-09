package kr.hhplus.be.server.infra.payment

import kr.hhplus.be.server.domain.payment.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<PaymentEntity, Long> {
}