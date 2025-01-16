package kr.hhplus.be.server.controller.common.exception

class PaymentException(
    override val message: String,
    cause: Throwable? = null,
) : RuntimeException(
    message,
    cause,
)
