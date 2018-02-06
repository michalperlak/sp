package pl.edu.agh.eaiib.io.sp.rest.processing

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.edu.agh.eaiib.io.sp.service.ProcessorService

@RestController
@RequestMapping("/api/process")
class ProcessorRestController(private val processorService: ProcessorService) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun process() {
        processorService.processData()
    }
}