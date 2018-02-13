package pl.edu.agh.eaiib.io.sp.data

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import pl.edu.agh.eaiib.io.sp.common.model.Reading
import pl.edu.agh.eaiib.io.sp.config.Configuration
import pl.edu.agh.eaiib.io.sp.data.publish.SensorDataPublisher
import pl.edu.agh.eaiib.io.sp.sensor.AndroidSensorEventHandlerAdapter
import java.util.concurrent.ConcurrentHashMap
import pl.edu.agh.eaiib.io.sp.common.model.Location as ModelLocation

class SensorDataCollector(private val sensorManager: SensorManager,
                          private val dataPublisher: SensorDataPublisher,
                          locationManager: LocationManager,
                          config: Configuration) {

    private val registeredSensors = ConcurrentHashMap<Int, AndroidSensorEventHandlerAdapter>()
    private var collectingEnabled = true

    init {
        val sensorsToCollectData = config.sensorsToCollectData
        val handler = DefaultSensorEventHandler(this)
        sensorsToCollectData.forEach {
            registerSensor(it, handler)
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0.01f, handler)
        } catch (e: SecurityException) {
            Log.d("SecurityException", e.localizedMessage)
        }
    }

    private fun registerSensor(sensorId: Int, sensorEventHandler: SensorEventHandler) {
        val androidSensorEventHandler = AndroidSensorEventHandlerAdapter(sensorId, sensorEventHandler)
        val sensor = sensorManager.getDefaultSensor(sensorId)
        registeredSensors[sensorId] = androidSensorEventHandler
        sensorManager.registerListener(androidSensorEventHandler, sensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    fun collectData(reading: Reading) {
        if (!collectingEnabled) {
            return
        }

        dataPublisher.addToQueue(reading)
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

class DefaultSensorEventHandler(private val dataCollector: SensorDataCollector) : SensorEventHandler, LocationListener {
    private var location: ModelLocation? = null
    private var accelerometerData: SensorData? = null
    private var gyroscopeData: SensorData? = null

    override fun onLocationChanged(location: Location?) {
        if (location == null) {
            return
        }

        this.location = ModelLocation(location.longitude, location.latitude)
        this.gyroscopeData = SensorData(Sensor.TYPE_GYROSCOPE, listOf(0.0f, 0.0f, 0.0f))
    }

    override fun handle(sensorEvent: SensorEvent) {
        if (location == null) {
            return
        }

        val data = convertToSensorData(sensorEvent)
        if (data.sensorType == Sensor.TYPE_GYROSCOPE) {
            gyroscopeData = data
        }

        if (data.sensorType == Sensor.TYPE_ACCELEROMETER) {
            accelerometerData = data
        }

        tryCollect()
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) = Unit

    override fun onProviderEnabled(provider: String?) = Unit

    override fun onProviderDisabled(provider: String?) = Unit

    private fun convertToSensorData(sensorEvent: SensorEvent): SensorData {
        return SensorData(sensorEvent.sensor.type, sensorEvent.values.toList())
    }

    private fun tryCollect() {
        val accelerometer = accelerometerData
        val gyroscope = gyroscopeData
        val location = this.location

        if (location == null || accelerometer == null || gyroscope == null) {
            return
        }

        val reading = Reading(Configuration.deviceId, accelerometer.values.map { it.toDouble() },
                gyroscope.values.map { it.toDouble() }, location)

        dataCollector.collectData(reading)
    }
}

data class SensorData(val sensorType: Int,
                      val values: List<Float>)
