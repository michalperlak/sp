package pl.edu.agh.eaiib.io.sp.data.publish

import pl.edu.agh.eaiib.io.sp.android.NetworkAvailabilityListener
import pl.edu.agh.eaiib.io.sp.common.SensorData
import pl.edu.agh.eaiib.io.sp.config.Configuration
import pl.edu.agh.eaiib.io.sp.rest.SensorApi
import java.util.concurrent.ConcurrentLinkedQueue

class SensorDataPublisher(private val sensorApi: SensorApi,
                          config: Configuration) : NetworkAvailabilityListener {

    private val publishQueue = ConcurrentLinkedQueue<SensorData>()
    private var publishEnabled = true

    fun startPublishingData() {
        publishEnabled = true
    }

    fun stopPublishingData() {
        publishEnabled = false
    }

    fun addToQueue(data: SensorData) {
        if (!publishEnabled) {
            return
        }

        publishQueue.add(data)
    }

    override fun networkAvailabilityChanged(networkAvailable: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}