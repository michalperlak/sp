package pl.edu.agh.eaiib.io.sp.common

data class SensorData(val deviceId: String,
                      val sensorType: Int,
                      val values: List<Float>,
                      val timestamp: Long)