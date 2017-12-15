package pl.edu.agh.eaiib.io.sp.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class SensorDataRouter {

    @Bean
    fun route(handler: SensorDataHandler): RouterFunction<ServerResponse> =
            router {
                "/sensorData".nest {
                    accept(MediaType.APPLICATION_JSON).nest {
                        GET("/", handler::fetchSensorData)
                        POST("/", handler::addSensorData)
                    }

                    accept(MediaType.APPLICATION_STREAM_JSON).nest {
                        GET("/", handler::sensorDataStream)
                    }
                }
            }
}