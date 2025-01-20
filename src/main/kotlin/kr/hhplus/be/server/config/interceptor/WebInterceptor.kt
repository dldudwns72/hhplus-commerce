package kr.hhplus.be.server.config.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.hhplus.be.server.domain.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class WebInterceptor(
    private val userRepository: UserRepository
) : HandlerInterceptor {
    val logger = LoggerFactory.getLogger("Interceptor Logger")

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestUri = request.requestURI
        logger.info("PreHandle: Request URI is $requestUri")

        val userId = extractUserIdFromUri(requestUri)
        userId?.also {
            userRepository.findById(userId.toLong())
                ?: throw IllegalArgumentException("User with ID $userId does not exist")
        }
        return true // true를 반환하면 다음으로 진행, false를 반환하면 요청 중단
    }

    fun extractUserIdFromUri(uri: String): String? {
        val regex = """/user/(\d+)""".toRegex()  // "/user/{userId}" 패턴에 맞는 정규식
        val matchResult = regex.find(uri)
        return matchResult?.groups?.get(1)?.value // {userId} 값 추출
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: org.springframework.web.servlet.ModelAndView?
    ) {
        logger.info("PostHandle: Handler method executed")
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        logger.info("AfterCompletion: Request completed with status ${response.status}")
    }
}