package pl.edu.agh.eaiib.io.sp.service

import pl.edu.agh.eaiib.io.sp.common.SensorData
import reactor.core.publisher.Flux
import java.time.Duration

interface SensorDataService {
    fun fetchSensorDataStream(period: Duration): Flux<SensorData>
}