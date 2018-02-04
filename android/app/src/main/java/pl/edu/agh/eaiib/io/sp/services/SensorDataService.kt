package pl.edu.agh.eaiib.io.sp.services

import android.hardware.SensorManager
import android.location.LocationManager
import pl.edu.agh.eaiib.io.sp.android.AndroidNetworkHelper
import pl.edu.agh.eaiib.io.sp.config.Configuration
import pl.edu.agh.eaiib.io.sp.data.SensorDataCollector
import pl.edu.agh.eaiib.io.sp.data.publish.SensorDataPublisher

class SensorDataService(sensorManager: SensorManager, locationManager: LocationManager, config: Configuration) {
    private val dataCollector: SensorDataCollector
    private val dataPublisher: SensorDataPublisher = SensorDataPublisher(config)

    init {
        dataCollector = SensorDataCollector(sensorManager, dataPublisher, locationManager, config)

        val networkHelper = ServicesUtil.getService(AndroidNetworkHelper::class.java)
        networkHelper.addNetworkStateListener(dataPublisher)
    }

    fun startCollectingData() = dataCollector.startCollectingData()
    fun stopCollectingData() = dataCollector.stopCollectingData()
    fun startPublishingData() = dataPublisher.startPublishingData()
    fun stopPublishingData() = dataPublisher.stopPublishingData()
}