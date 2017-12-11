package pl.edu.agh.eaiib.io.sp.common

data class SensorData(val sensorId: String,
                      val values: List<Float>,
                      val timestamp: Long)