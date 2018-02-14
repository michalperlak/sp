package pl.edu.agh.eaiib.io.sp.common.model

data class Location(val longitude: Double = 0.0,
                    val latitude: Double = 0.0) {

    override fun hashCode(): Int {
        return 31 * (longitude * 10000000 + latitude * 10000000).toInt()
    }

}