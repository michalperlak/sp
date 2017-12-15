package pl.edu.agh.eaiib.io.sp.repositories

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import pl.edu.agh.eaiib.io.sp.common.SensorData

interface SensorDataRepository : ReactiveMongoRepository<SensorData, String>