package kr.hhplus.be.server.config.filter

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig {

    @Bean
    fun loggingFilter(): FilterRegistrationBean<WebFilter> {
        val registrationBean = FilterRegistrationBean<WebFilter>()
        registrationBean.filter = WebFilter()
        // registrationBean.isEnabled = true
        registrationBean.addUrlPatterns("/api/*")
        return registrationBean
    }
}