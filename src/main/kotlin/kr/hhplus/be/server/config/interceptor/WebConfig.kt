package kr.hhplus.be.server.config.interceptor

import kr.hhplus.be.server.domain.user.UserRepository
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val userRepository: UserRepository
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(WebInterceptor(userRepository))
            .addPathPatterns("/api/**")  // 특정 경로에만 적용
            .excludePathPatterns("/api/auth/**")  // 제외 경로
    }
}