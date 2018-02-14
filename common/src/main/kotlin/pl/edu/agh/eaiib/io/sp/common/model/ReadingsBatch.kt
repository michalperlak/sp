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
                    .append(it.key.longitude)
                    .append(",")
                    .append(it.key.latitude)
                    .append(":")

            it.value.forEach {
                stringBuilder.append(it.accelerometer.joinToString(separator = ","))
                stringBuilder.append(";")
                stringBuilder.append(it.gyroscope.joinToString(separator = ","))
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

        val elements = encodedReadings.split("$")
                .map { it.replace("$", "") }
                .filter { it.isNotEmpty() }

        val result = ArrayList<Reading>()

        for (element in elements) {
            println(element)
            val locationString = element.substring(0, element.indexOf(":"))
            val longitude = locationString.substring(0, locationString.indexOf(",")).toDouble()
            val latitude = locationString.substring(locationString.indexOf(",") + 1).toDouble()

            val location = Location(longitude, latitude)

            val valuesStrings = element.substring(locationString.length + 1)
                    .split("#")
                    .filter { it.isNotEmpty() }

            println(valuesStrings)

            for (value in valuesStrings) {
                val fragments = value.split(";")
                val accelerometer = fragments[0].split(",")
                        .map { it.toDouble() }

                val gyroscope = fragments[1].split(",")
                        .map { it.toDouble() }

                val timestamp = fragments[2].toLong()
                result.add(Reading(deviceId, accelerometer, gyroscope, location, timestamp))
            }
        }

        return result
    }
}