package kr.hhplus.be.server.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI().components(Components()).info(configurationInfo())
    }


    private fun configurationInfo(): Info {
        return Info().title("E-Commerce API Swagger").description("OpenApi3 - E-Commerce API 명세서").version("1.0.0")
    }
}