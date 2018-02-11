package pl.edu.agh.eaiib.io.sp.service

import org.springframework.stereotype.Service
import pl.edu.agh.eaiib.io.sp.analysis.DataAnalyzer
import pl.edu.agh.eaiib.io.sp.common.model.ProcessedData
import pl.edu.agh.eaiib.io.sp.repositories.ProcessedDataRepository
import pl.edu.agh.eaiib.io.sp.repositories.ReadingsRepository
import pl.edu.agh.eaiib.io.sp.utils.evaluate
import java.util.stream.Collectors

@Service
class ProcessorService(private val processedDataRepository: ProcessedDataRepository,
                       private val readingsRepository: ReadingsRepository,
                       private val dataAnalyzer: DataAnalyzer) {


    fun clean() {
        processedDataRepository.deleteAll()
    }

    fun processData(deviceId: String, startTimestamp: Long, endTimestamp: Long) {
        val toProcess = readingsRepository.findAll()
                .asSequence()
                .filter { it.deviceId == deviceId }
                .filter { it.timestamp in startTimestamp..endTimestamp }

        val processed = dataAnalyzer.process(toProcess)
        processedDataRepository.saveAll(processed)
    }

    fun processData(deviceId: String, start: Long) {
        val readingList = readingsRepository.findAll()
        val readingStream = readingList.stream()
                .filter { (deviceId1) -> deviceId1 == deviceId }
                .filter { x -> x.timestamp >= start }

        val min = readingStream.mapToDouble { x -> x.accelerometer[2] }.min().orElse(0.0)
        val max = readingStream.mapToDouble { x -> x.accelerometer[2] }.max().orElse(20.0)

        val processedDataList = readingStream.map { x -> ProcessedData(x.location, evaluate(x.accelerometer[2], min, max)) }
                .collect(Collectors.toList())

        processedDataRepository.saveAll(processedDataList)
    }
}
