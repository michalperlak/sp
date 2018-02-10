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

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    fun processTest() {
        processorService.processData("87e1e1fd70a3bb9d", 1518111300000)
    }

    @GetMapping("/cleanbase")
    @ResponseStatus(HttpStatus.OK)
    fun cleanBase() {
        processorService.clean();
    }
}