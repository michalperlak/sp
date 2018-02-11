package pl.edu.agh.eaiib.io.sp.analysis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import pl.edu.agh.eaiib.io.sp.common.model.ProcessedData
import pl.edu.agh.eaiib.io.sp.common.model.Reading
import weka.classifiers.functions.SMO
import weka.core.Instances
import weka.core.converters.ConverterUtils
import weka.classifiers.Classifier as WekaClassifier
import weka.classifiers.functions.supportVector.RBFKernel


@Component
class DataAnalyzer(private val classifier: Classifier) {

    fun process(readings: Sequence<Reading>): List<ProcessedData> {
        val dataWindow = createDataWindow(readings)
        val quality = classifier.classify(dataWindow)
        return listOf(ProcessedData(dataWindow.startLocation, quality.rate), ProcessedData(dataWindow.endLocation, quality.rate))
    }
}

@Configuration
@PropertySource("classpath:classifier.properties")
@ConfigurationProperties(prefix = "classifier")
class ClassifierConfiguration {
    var trainingDataFile: String = ""
    var kernelCacheSize: Int = 0
    var kernelGammaFactor: Double = 0.01

    @Bean
    fun getClassifier(): Classifier = Classifier(buildClassifier(getTrainingSet()))

    private fun buildClassifier(trainingSet: Instances): WekaClassifier {
        val classifier = SMO()
        classifier.kernel = RBFKernel(trainingSet, kernelCacheSize, kernelGammaFactor)
        return classifier
    }

    private fun getTrainingSet(): Instances {
        val dataFilePath = trainingDataFile
        val dataFile = this.javaClass.classLoader.getResource(dataFilePath).file
        val data = ConverterUtils.DataSource(dataFile).dataSet

        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1)
        }

        return data
    }
}

class Classifier(val wekaClassifier: WekaClassifier) {

    fun classify(dataWindow: DataWindow): QualityLabel {
        return QualityLabel.AVERAGE //tODO
    }
}


enum class QualityLabel(val rate: Int) {
    BAD(1), AVERAGE(2), GOOD(3)
}