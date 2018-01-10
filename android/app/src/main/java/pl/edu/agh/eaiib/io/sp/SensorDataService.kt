package pl.edu.agh.eaiib.io.sp

import android.hardware.SensorManager
import pl.edu.agh.eaiib.io.sp.android.AndroidNetworkHelper
import pl.edu.agh.eaiib.io.sp.config.Configuration
import pl.edu.agh.eaiib.io.sp.data.SensorDataCollector
import pl.edu.agh.eaiib.io.sp.data.publish.SensorDataPublisher

class SensorDataService(sensorManager: SensorManager, config: Configuration) {
    private val dataCollector: SensorDataCollector
    private val dataPublisher: SensorDataPublisher = SensorDataPublisher(config)

    init {
        dataCollector = SensorDataCollector(sensorManager, dataPublisher, config)

        val networkHelper = ServicesUtil.getService(AndroidNetworkHelper::class.java)
        networkHelper.addNetworkStateListener(dataPublisher)

        ServicesUtil.registerService(this)
    }

    fun startCollectingData() = dataCollector.startCollectingData()
    fun stopCollectingData() = dataCollector.stopCollectingData()
    fun startPublishingData() = dataPublisher.startPublishingData()
    fun stopPublishingData() = dataPublisher.stopPublishingData()
}