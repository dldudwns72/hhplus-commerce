package kr.hhplus.be.server.controller.common

class SingleResponse<T>(
    val result: String,
    val data: T?
) {

    companion object {
        fun <T> execute(block: () -> T): SingleResponse<T> {
            return try {
                SingleResponse(result = "SUCCESS", data = block())
            } catch (e: Exception) {
                SingleResponse(result = "FAIL", data = null)
            }
        }
    }

}