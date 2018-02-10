package pl.edu.agh.eaiib.io.sp.service

import org.springframework.stereotype.Service
import pl.edu.agh.eaiib.io.sp.common.model.ProcessedData
import pl.edu.agh.eaiib.io.sp.repositories.ProcessedDataRepository

@Service
class ProcessedDataService(private val processedDataRepository: ProcessedDataRepository) {
    fun getAll(): List<ProcessedData> {
        return processedDataRepository.findAll()
    }
}