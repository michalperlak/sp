package pl.edu.agh.eaiib.io.sp.utils

import org.apache.commons.math3.stat.descriptive.rank.Median
import pl.edu.agh.eaiib.io.sp.analysis.computeFeatures
import weka.core.Attribute
import weka.core.DenseInstance
import weka.core.Instances
import weka.core.converters.ArffSaver
import java.io.File
import java.util.concurrent.ThreadLocalRandom

fun main(args: Array<String>) {
    if (args.size < 2) {
        throw IllegalStateException("Output file path and number of rows are required")
    }

    val outputFile = File(args[0])
    val rows = args[1].toInt()

    val data = createTrainingData(rows)
    saveResults(data, outputFile)
}

fun createTrainingData(rows: Int): Instances {
    val instances = Instances("training", createAttributes(), 0)
    instances.setClassIndex(instances.numAttributes() - 1)

    val values = generateValues(rows)
    values.forEach {
        val features = computeFeatures(it)
        val instance = DenseInstance(7)
        instance.setValue(0, features.mean)
        instance.setValue(1, features.absMean)
        instance.setValue(2, features.variance)
        instance.setValue(3, features.minMaxAbsSum)
        instance.setValue(4, features.energy)
        instance.setValue(5, features.entropy)

        val classValue = getClassValue(it)
        instance.setDataset(instances)
        instance.setClassValue(classValue.toString())
        instances.add(instance)
    }

    return instances
}

private fun generateValues(rows: Int): List<DoubleArray> {
    return IntRange(0, rows - 1)
            .asSequence()
            .map { randomArray(1000) }
            .toList()
}

private fun randomArray(size: Int): DoubleArray {
    return IntRange(0, size - 1).map { randomRead() }.toDoubleArray()
}

private fun randomRead(): Double {
    return ThreadLocalRandom.current().nextDouble(MIN_RANDOM_READ, MAX_RANDOM_READ)
}

private fun getClassValue(values: DoubleArray): Int {
    val min = values.min()!!
    val max = values.max()!!
    val median = Median()
    val rate = evaluate(median.evaluate(values), min, max)

    if (rate >= HIGHEST_RATE_THRESHOLD) {
        return HIGHEST_RATE
    } else if (rate >= AVERAGE_RATE_THRESHOLD) {
        return AVERAGE_RATE
    }

    return LOWEST_RATE
}

private fun saveResults(results: Instances, outputFile: File) {
    val saver = ArffSaver()
    saver.instances = results
    saver.setFile(outputFile)
    saver.writeBatch()
}

private fun createAttributes(): ArrayList<Attribute> = arrayListOf(
        Attribute("mean"),
        Attribute("absMean"),
        Attribute("variance"),
        Attribute("minMaxAbsSum"),
        Attribute("energy"),
        Attribute("entropy"),
        Attribute("quality", listOf("1", "2", "3")))

private const val MIN_RANDOM_READ = 10.0
private const val MAX_RANDOM_READ = 12.5

private const val HIGHEST_RATE_THRESHOLD = 9.0
private const val AVERAGE_RATE_THRESHOLD = 6.0

private const val HIGHEST_RATE = 3
private const val AVERAGE_RATE = 2
private const val LOWEST_RATE = 1
