package pl.edu.agh.eaiib.io.sp.service.impl

import org.springframework.stereotype.Service
import pl.edu.agh.eaiib.io.sp.common.SensorData
import pl.edu.agh.eaiib.io.sp.repositories.SensorDataRepository
import pl.edu.agh.eaiib.io.sp.service.SensorDataService
import reactor.core.publisher.Flux
import java.time.Duration

@Service
class SensorDataServiceImpl(private val sensorDataRepository: SensorDataRepository) : SensorDataService {

    override fun fetchSensorDataStream(period: Duration): Flux<SensorData> {
        TODO("not implemented")
    }
}