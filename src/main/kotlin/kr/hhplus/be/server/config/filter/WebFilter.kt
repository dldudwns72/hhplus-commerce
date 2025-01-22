package kr.hhplus.be.server.config.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.web.filter.OncePerRequestFilter
import java.time.Instant

class WebFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 1. 요청에 대한 traceId 생성 (혹은 기존 traceId를 요청에서 추출)
        val traceId = generateTraceId()
        MDC.put("traceId", traceId)  // MDC에 traceId를 저장

        // 2. 요청 처리 시작 시간 기록 (doFilter 이전에 호출)
        val startTime = Instant.now().toEpochMilli()

        try {
            // 3. 요청 처리 (다음 필터 또는 서블릿으로 이동)
            filterChain.doFilter(request, response)
        } finally {
            // 4. 요청 처리 종료 시간 기록 (doFilter 이후에 호출)
            val endTime = Instant.now().toEpochMilli()

            // 5. 요청/응답 로깅 (doFilter 이후에 호출)
            logger.info("Request URI: ${request.requestURI}, TraceId: $traceId, Start Time: $startTime, End Time: $endTime, Response Status: ${response.status}")

            // 6. MDC에서 traceId 제거 (중요: 요청이 끝난 후에는 MDC에서 값을 제거해야 함)
            MDC.remove("traceId")
        }
    }

    private fun generateTraceId(): String {
        return java.util.UUID.randomUUID().toString()
    }

}