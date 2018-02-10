package pl.edu.agh.eaiib.io.sp.rest.processing

import org.springframework.web.bind.annotation.*
import pl.edu.agh.eaiib.io.sp.common.model.ProcessedData
import pl.edu.agh.eaiib.io.sp.service.ProcessedDataService

@RestController
@RequestMapping("/api/processedData")
class ProcessedDataRestController(private val processedDataService: ProcessedDataService) {
    @GetMapping
    fun getAll(): List<ProcessedData> {
        return processedDataService.getAll()
    }
}