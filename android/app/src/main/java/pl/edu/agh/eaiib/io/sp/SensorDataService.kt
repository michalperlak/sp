package pl.edu.agh.eaiib.io.sp

import android.hardware.SensorManager
import pl.edu.agh.eaiib.io.sp.data.SensorDataCollector
import pl.edu.agh.eaiib.io.sp.config.Configuration
import pl.edu.agh.eaiib.io.sp.data.publish.SensorDataPublisher
import pl.edu.agh.eaiib.io.sp.rest.SensorApi

class SensorDataService(sensorManager: SensorManager, config: Configuration) {
    private val dataCollector: SensorDataCollector
    private val dataPublisher: SensorDataPublisher

    init {
        val sensorApi = SensorApi.create(config.serverBaseUrl)
        dataPublisher = SensorDataPublisher(sensorApi, config)
        dataCollector = SensorDataCollector(sensorManager, dataPublisher, config)
    }

    fun startCollectingData() = dataCollector.startCollectingData()
    fun stopCollectingData() = dataCollector.stopCollectingData()
    fun startPublishingData() = dataPublisher.startPublishingData()
    fun stopPublishingData() = dataPublisher.stopPublishingData()
}