package pl.edu.agh.eaiib.io.sp.common

data class SensorData(val deviceId: String = "",
                      val sensorType: Int = 1,
                      val values: List<Float>,
                      val timestamp: Long)