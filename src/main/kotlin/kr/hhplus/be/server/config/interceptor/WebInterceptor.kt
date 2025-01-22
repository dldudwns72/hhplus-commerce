package kr.hhplus.be.server.config.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.hhplus.be.server.controller.common.SingleResponse
import kr.hhplus.be.server.domain.user.UserEntity
import kr.hhplus.be.server.domain.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class WebInterceptor(
    private val userRepository: UserRepository,
    private val objectMapper: ObjectMapper
) : HandlerInterceptor {
    val logger = LoggerFactory.getLogger("Interceptor Logger")

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val userId: String? = request.getHeader("user-id")
        val user: UserEntity? = userId?.let { id ->
            userRepository.findById(id.toLong())
        }

        // HandlerInterceptor 에서 요청이 컨트롤러로 전달되기 전에 실행되기 때문에 preHandler에서 throw를 진행해도 예외가 전달되지 않고 DispatcherServlet 의해 처리된다.
        if (userId.isNullOrBlank() || user == null) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = Charsets.UTF_8.name()

            val errorResponse = SingleResponse.error("유저 정보가 올바르지 않습니다.")
            // return false 처리 하지 않으면 response 만들어주는거 getWriter 두번 호출하게 된다.
            response.writer.write(objectMapper.writeValueAsString(errorResponse))
            return false // false를 반환하면 요청 중단
        }
        return true // true를 반환하면 다음으로 진행,
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