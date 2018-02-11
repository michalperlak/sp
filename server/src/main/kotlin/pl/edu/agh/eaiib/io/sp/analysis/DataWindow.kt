package pl.edu.agh.eaiib.io.sp.analysis

import pl.edu.agh.eaiib.io.sp.common.model.Location
import pl.edu.agh.eaiib.io.sp.common.model.Reading

data class DataWindow(val startLocation: Location,
                      val endLocation: Location,
                      val xAcceleration: List<Double>,
                      val yAcceleration: List<Double>,
                      val zAcceleration: List<Double>,
                      val timestamps: List<Long>,
                      val features: DataWindowFeatures)

fun createDataWindow(readings: Sequence<Reading>): DataWindow {
    val sortedReadings = readings.sortedBy { it.timestamp }
    val locations = sortedReadings.map { it.location }

    val startLocation = locations.first()
    val endLocation = locations.last()

    val xAcceleration = sortedReadings.map { it.accelerometer[0] }.toList()
    val yAcceleration = sortedReadings.map { it.accelerometer[1] }.toList()
    val zAcceleration = sortedReadings.map { it.accelerometer[2] }.toList()

    val timestamps = sortedReadings.map { it.timestamp }.toList()
    val features = computeFeatures(yAcceleration.toDoubleArray())

    return DataWindow(startLocation, endLocation, xAcceleration, yAcceleration, zAcceleration, timestamps, features)
}