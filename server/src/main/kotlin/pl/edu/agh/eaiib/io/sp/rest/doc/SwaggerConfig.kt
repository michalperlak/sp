package pl.edu.agh.eaiib.io.sp.rest.doc

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/.*"))
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("EAIIB AGH SP2 REST API")
                .license("EAIIB AGH SP2 License")
                .licenseUrl("http://eaiib.agh.edu.pl")
                .termsOfServiceUrl("http://eaiib.agh.edu.pl")
                .version("1.0")
                .description("REST API for SP2 project - EAIIB AGH")
                .build()
    }
}