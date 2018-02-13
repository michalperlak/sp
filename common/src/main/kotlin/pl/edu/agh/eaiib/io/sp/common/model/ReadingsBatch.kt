package pl.edu.agh.eaiib.io.sp.common.model

data class ReadingsBatch(val deviceId: String = "",
                         val values: String = "")

object ReadingsBatchEncoder {
    fun encode(readings: Iterable<Reading>): String {
        val groupedByLocation = readings.asSequence()
                .groupBy { it.location }
                .toMap()

        val stringBuilder = StringBuilder()
        groupedByLocation.forEach {
            stringBuilder.append("$")
                    .append(it.key)
                    .append(":")

            it.value.forEach {
                stringBuilder.append(it.accelerometer.joinToString(separator = ",", prefix = "[", postfix = "]"))
                stringBuilder.append(";")
                stringBuilder.append(it.gyroscope.joinToString(separator = ",", prefix = "[", postfix = "]"))
                stringBuilder.append(";")
                stringBuilder.append(it.timestamp)
                stringBuilder.append("#")
            }
        }

        return stringBuilder.toString()
    }
}

object ReadingsBatchDecoder {
    fun decode(deviceId: String, encodedReadings: String): List<Reading> {
        return emptyList() //todo
    }
}