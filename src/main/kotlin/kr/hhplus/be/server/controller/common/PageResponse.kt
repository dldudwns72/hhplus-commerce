package kr.hhplus.be.server.controller.common

import org.springframework.data.domain.Page

data class PageResponse<T>(
    val result: String = "SUCCESS", // Enum으로 관리?
    val data: Page<T>
) {

    companion object {
        fun <T> execute(block: () -> Page<T>): PageResponse<T> {
            return PageResponse(data = block())
        }
    }
}