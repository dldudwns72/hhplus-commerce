package kr.hhplus.be.server.config.filter

import jakarta.servlet.*
import jakarta.servlet.FilterConfig
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class WebFilter : Filter {

    val logger = LoggerFactory.getLogger("Filter Logger")

    // 애플리케이션 시작 최초 한 번만 실행되어 요청 처리 전 초기화 작업 수행
    override fun init(filterConfig: FilterConfig?) {
        super.init(filterConfig)
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, filterChain: FilterChain?) {
        val startTime = System.currentTimeMillis()
        val endTime = System.currentTimeMillis()
        logger.info("Request processed in ${endTime - startTime} ms")
        val httpRequest = request as HttpServletRequest
        logger.info("Request Mapping - URI: ${request.requestURI}, Method: ${httpRequest.method}")
        filterChain?.doFilter(request, response)  // 다음 필터 진행
    }


    // 애플리케이션 종료되거나 필터가 더이상 필요하지 않을 때 호출
    override fun destroy() {
        /**
         * 1. 자원정리
         * 2. 캐시정리
         * 3. 기타 등등
         */
    }
}