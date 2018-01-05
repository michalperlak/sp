package pl.edu.agh.eaiib.io.sp.data

import android.hardware.SensorEvent
import android.hardware.SensorManager
import pl.edu.agh.eaiib.io.sp.common.SensorData
import pl.edu.agh.eaiib.io.sp.config.Configuration
import pl.edu.agh.eaiib.io.sp.data.publish.SensorDataPublisher
import pl.edu.agh.eaiib.io.sp.sensor.AndroidSensorEventHandlerAdapter
import java.util.concurrent.ConcurrentHashMap

class SensorDataCollector(private val sensorManager: SensorManager,
                          private val dataPublisher: SensorDataPublisher,
                          config: Configuration) {

    private val registeredSensors = ConcurrentHashMap<Int, AndroidSensorEventHandlerAdapter>()
    private var collectingEnabled = true

    init {
        val sensorsToCollectData = config.sensorsToCollectData
        sensorsToCollectData.forEach {
            registerSensor(it, DefaultSensorEventHandler(this))
        }
    }

    private fun registerSensor(sensorId: Int, sensorEventHandler: SensorEventHandler) {
        val androidSensorEventHandler = AndroidSensorEventHandlerAdapter(sensorId, sensorEventHandler)
        val sensor = sensorManager.getDefaultSensor(sensorId)
        registeredSensors[sensorId] = androidSensorEventHandler
        sensorManager.registerListener(androidSensorEventHandler, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    fun collectData(data: SensorData) {
        if (!collectingEnabled) {
            return
        }

        dataPublisher.addToQueue(data)
    }

    fun startCollectingData() {
        collectingEnabled = true
    }

    fun stopCollectingData() {
        collectingEnabled = false
    }
}

interface SensorEventHandler {
    fun handle(sensorEvent: SensorEvent)
}

class DefaultSensorEventHandler(private val dataCollector: SensorDataCollector) : SensorEventHandler {
    override fun handle(sensorEvent: SensorEvent) {
        val data = convertToSensorData(sensorEvent)
        dataCollector.collectData(data)
    }

    private fun convertToSensorData(sensorEvent: SensorEvent): SensorData {
        return SensorData(sensorEvent.sensor.type, sensorEvent.values.toList(), sensorEvent.timestamp)
    }
}
