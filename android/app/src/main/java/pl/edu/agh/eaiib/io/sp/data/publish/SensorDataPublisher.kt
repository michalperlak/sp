package pl.edu.agh.eaiib.io.sp.data.publish

import pl.edu.agh.eaiib.io.sp.ServicesUtil
import pl.edu.agh.eaiib.io.sp.android.AndroidNetworkHelper
import pl.edu.agh.eaiib.io.sp.android.NetworkAvailabilityListener
import pl.edu.agh.eaiib.io.sp.common.SensorData
import pl.edu.agh.eaiib.io.sp.config.Configuration
import pl.edu.agh.eaiib.io.sp.rest.SensorApi
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class SensorDataPublisher(config: Configuration) : NetworkAvailabilityListener {

    private val sensorApi = SensorApi.create(config.serverBaseUrl)
    private val publishQueue = ConcurrentLinkedQueue<SensorData>()

    private val executor: Executor = Executors.newSingleThreadExecutor { runnable ->
        val thread = Thread(runnable)
        thread.isDaemon = true

        thread
    }

    private var publishEnabled = true
    private var networkAvailable = ServicesUtil.getService(AndroidNetworkHelper::class.java).isNetworkAvailable()

    init {
        executor.execute({
            while (true) {
                tryPublishData()
            }
        })
    }

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
        this.networkAvailable = networkAvailable
    }

    private fun tryPublishData() {
        if (!networkAvailable || publishQueue.isEmpty()) {
            return
        }

        val data = publishQueue.poll()
        sensorApi.addSensorData(data)
    }
}