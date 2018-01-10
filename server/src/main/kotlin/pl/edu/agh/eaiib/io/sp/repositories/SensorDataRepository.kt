package pl.edu.agh.eaiib.io.sp.repositories

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "sensors", path = "sensorData")
interface SensorDataRepository : MongoRepository<SensorData, String>

data class SensorData(private val deviceId: String,
                      private val sensorType: Int,
                      private val values: List<Float>,
                      private val timestamp: Long) {
    @Id
    val id: String = "$deviceId-$sensorType-$timestamp"
}

