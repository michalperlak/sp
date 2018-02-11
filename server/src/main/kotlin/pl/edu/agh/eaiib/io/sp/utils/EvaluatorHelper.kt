package pl.edu.agh.eaiib.io.sp.utils

fun evaluate(accRead: Double, min: Double, max: Double): Int {
    val conditionEvaluator = getConditionEvaluator(accRead, min, max)
    for (i in 0 until MAX_RATE) {
        val rate = MAX_RATE - i
        if (conditionEvaluator.invoke(i)) {
            return rate
        }
    }

    return MIN_RATE
}

private fun getConditionEvaluator(accRead: Double, min: Double, max: Double): (Int) -> Boolean {
    val diff = Math.abs(if (accRead <= EARTH_ACCELERATION) EARTH_ACCELERATION - min else max - EARTH_ACCELERATION)
    return { i ->
        if (accRead <= EARTH_ACCELERATION) (accRead > EARTH_ACCELERATION - (i + 1) * (diff / MAX_RATE))
        else (accRead < EARTH_ACCELERATION + (i + 1) * (diff / MAX_RATE))
    }
}

private const val EARTH_ACCELERATION = 9.81
private const val MAX_RATE = 10
private const val MIN_RATE = 1