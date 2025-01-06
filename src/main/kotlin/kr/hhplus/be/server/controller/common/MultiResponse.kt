package kr.hhplus.be.server.controller.common

object MultiResponse {

    fun <T> execute(item: T) {
        println("item $item")
    }
}