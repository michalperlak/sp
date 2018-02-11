package pl.edu.agh.eaiib.io.sp.analysis

import org.apache.commons.math3.complex.Complex
import org.apache.commons.math3.transform.DftNormalization
import org.apache.commons.math3.transform.FastFourierTransformer
import org.apache.commons.math3.transform.TransformType

data class DataWindowFeatures(val mean: Double,
                              val absMean: Double,
                              val variance: Double,
                              val minMaxAbsSum: Double,
                              val energy: Double,
                              val entropy: Double)

fun computeFeatures(values: DoubleArray): DataWindowFeatures {
    val mean = mean(values)
    val absMean = absMean(values)
    val variance = variance(values)
    val minMaxAbsSum = minMaxAbsSum(values)
    val energy = energy(values)
    val entropy = entropy(values)

    return DataWindowFeatures(mean, absMean, variance, minMaxAbsSum, energy, entropy)
}

fun mean(values: DoubleArray): Double = values.average()

fun absMean(values: DoubleArray): Double {
    return values.map { Math.abs(it) }.average()
}

fun variance(values: DoubleArray): Double {
    val avg = values.average()
    val sum = values.map { (it - avg) * (it - avg) }.sum()

    return sum / values.size
}

fun minMaxAbsSum(values: DoubleArray): Double {
    if (values.isEmpty()) {
        throw IllegalArgumentException("Array cannot be empty!")
    }

    return Math.abs(values.min()!!) + Math.abs(values.max()!!)
}

fun energy(values: DoubleArray): Double {
    val transformer = FastFourierTransformer(DftNormalization.STANDARD)
    val fft = transformer.transform(values, TransformType.FORWARD)

    val sum = fft.map { Math.pow(it.abs(), 2.0) }.sum()
    return sum / fft.size
}

fun entropy(values: DoubleArray): Double {
    val transformer = FastFourierTransformer(DftNormalization.STANDARD)
    val fft = transformer.transform(values, TransformType.FORWARD)

    val powerSpectrumSum = fft.map { powerSpectrum(it) }.sum()
    val powerSpectrumDistribution = fft.map { powerSpectrum(it) / powerSpectrumSum }
    return -(powerSpectrumDistribution.map { it * Math.log(it) }.sum())
}

fun powerSpectrum(fft: Complex): Double = Math.pow(fft.abs(), 2.0)
