package pl.edu.agh.eaiib.io.sp.data

import android.hardware.SensorEvent
import android.hardware.SensorManager
import pl.edu.agh.eaiib.io.sp.sensor.AndroidSensorEventHandlerAdapter
import java.util.concurrent.ConcurrentHashMap

class SensorDataCollector(private val sensorManager: SensorManager) {

    private val registeredSensors = ConcurrentHashMap<Int, AndroidSensorEventHandlerAdapter>()

    fun registerSensor(sensorId: Int, sensorEventHandler: SensorEventHandler) {
        val androidSensorEventHandler = AndroidSensorEventHandlerAdapter(sensorId, sensorEventHandler)
        val sensor = sensorManager.getDefaultSensor(sensorId)
        registeredSensors[sensorId] = androidSensorEventHandler
        sensorManager.registerListener(androidSensorEventHandler, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    fun unregisterSensor(androidSensorEventHandler: AndroidSensorEventHandlerAdapter) {
        sensorManager.unregisterListener(androidSensorEventHandler)
    }
}

interface SensorEventHandler {
    fun handle(sensorEvent: SensorEvent)
}
