package pl.edu.agh.eaiib.io.sp.config

import android.hardware.Sensor

object Configuration {
    const val serverBaseUrl: String = "http://mikepq.nazwa.pl:9090/api/"

    val sensorsToCollectData: List<Int> = listOf(
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_GYROSCOPE)

    var deviceId = ""
}

