package pl.edu.agh.eaiib.io.sp.web

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pl.edu.agh.eaiib.io.sp.service.SensorDataService
import reactor.core.publisher.Mono

@Component
class SensorDataHandler(private val sensorDataService: SensorDataService) {

    fun fetchSensorData(request: ServerRequest): Mono<ServerResponse> {
        //todo implement

        return Mono.empty()
    }

    fun sensorDataStream(request: ServerRequest): Mono<ServerResponse> {
        //todo implement

        return Mono.empty()
    }

    fun addSensorData(request: ServerRequest): Mono<ServerResponse> {
        //todo implement

        return Mono.empty()
    }

}