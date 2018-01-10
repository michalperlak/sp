package pl.edu.agh.eaiib.io.sp.config

import android.hardware.Sensor

object Configuration {
    val serverBaseUrl: String = "http://localhost:9090/sensors"
    val sensorsToCollectData: List<Int> = listOf(
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_GYROSCOPE,
            Sensor.TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_GRAVITY)

    var deviceId = ""
}

