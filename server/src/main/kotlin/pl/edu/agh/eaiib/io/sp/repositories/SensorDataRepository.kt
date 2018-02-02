package pl.edu.agh.eaiib.io.sp.repositories

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import pl.edu.agh.eaiib.io.sp.models.SensorReadData

@RepositoryRestResource(collectionResourceRel = "sensors", path = "sensorData")
interface SensorDataRepository : MongoRepository<SensorData, String>

data class SensorData(private val deviceId: String,
                      private val values: List<SensorReadData>,
                      private val timestamp: Long) {
    @Id
    val id: String = "$deviceId-$timestamp"
}

