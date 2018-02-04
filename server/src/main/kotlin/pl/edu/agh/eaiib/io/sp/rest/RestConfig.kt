package pl.edu.agh.eaiib.io.sp.rest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.Filter

@Configuration
class RestConfig {

    @Bean
    fun configureCORS(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry?) {
                registry?.addMapping("/**")
            }
        }
    }


    @Bean
    fun logFilter(): Filter {
        val filter = CommonsRequestLoggingFilter()
        filter.setIncludeClientInfo(true)
        filter.isIncludeHeaders = true
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(true)
        filter.setMaxPayloadLength(5120)
        return filter
    }
}