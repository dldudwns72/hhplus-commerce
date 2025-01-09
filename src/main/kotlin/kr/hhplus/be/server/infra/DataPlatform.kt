package kr.hhplus.be.server.infra

import kr.hhplus.be.server.domain.payment.PaymentEntity
import org.springframework.stereotype.Component

@Component
class DataPlatform {

    companion object {
        fun send(payment: PaymentEntity): Boolean {
            return if (payment.id > 0) true
            else throw IllegalArgumentException("결제에 실패하였습니다.")
        }
    }
}