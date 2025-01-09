package kr.hhplus.be.server.controller.common

data class MultiResponse<T>(
    val result: String = "SUCCESS", // Enum으로 관리?
    val data: List<T> = listOf()
) {

    companion object {
        fun <T> execute(block: () -> List<T>): MultiResponse<T> {
            return MultiResponse(data = block())
        }
    }
}