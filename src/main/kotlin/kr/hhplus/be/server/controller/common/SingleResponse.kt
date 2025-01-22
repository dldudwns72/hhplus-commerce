package kr.hhplus.be.server.controller.common


data class SingleResponse<T>(
    val result: String = "SUCCESS", // Enum으로 관리?
    val data: T
) {

    companion object {
        fun <T> execute(block: () -> T): SingleResponse<T> {
            return SingleResponse(data = block())
        }

        fun error(message: String): SingleResponse<String> = SingleResponse(result = "ERROR", data = message)
    }
}