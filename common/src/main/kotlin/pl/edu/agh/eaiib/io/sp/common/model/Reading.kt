package pl.edu.agh.eaiib.io.sp.common.model

data class Reading(val accelerometer: List<Double> = emptyList(),
                   val gyroscope: List<Double> = emptyList(),
                   val location: Location = Location())