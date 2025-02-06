package kr.hhplus.be.server.config.interceptor

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val webInterceptor: WebInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(webInterceptor)
            .addPathPatterns("/api/**")  // 특정 경로에만 적용
            .excludePathPatterns("/api/auth/**")  // 제외 경로
            .excludePathPatterns("/api/v1/product/**")  // 제외 경로
    }
}