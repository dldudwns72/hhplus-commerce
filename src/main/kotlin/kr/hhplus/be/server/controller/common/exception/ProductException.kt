package kr.hhplus.be.server.controller.common.exception

class ProductException(
    override val message: String,
    cause: Throwable? = null,
) : RuntimeException(
    message,
    cause,
)
