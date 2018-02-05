package pl.edu.agh.eaiib.io.sp.common.model

data class Reading(val deviceId: String = "",
                   val accelerometer: List<Double> = emptyList(),
                   val gyroscope: List<Double> = emptyList(),
                   val location: Location = Location(),
                   val timestamp: Long = System.currentTimeMillis())