package pl.edu.agh.eaiib.io.sp.models

data class SensorReadData(private val positionFromGPS: PositionFromGPS,
                          private val verticalDeviation: Float) {
}
