package pl.edu.agh.eaiib.io.sp.service

import org.springframework.stereotype.Service
import pl.edu.agh.eaiib.io.sp.repositories.ProcessedDataRepository
import pl.edu.agh.eaiib.io.sp.repositories.ReadingsRepository

@Service
class ProcessorService(private val readingsRepository: ReadingsRepository,
                       private val processedDataRepository: ProcessedDataRepository) {
    fun processData() {
    }
}