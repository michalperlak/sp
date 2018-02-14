package pl.edu.agh.eaiib.io.sp.rest.readings

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.eaiib.io.sp.common.model.Reading
import pl.edu.agh.eaiib.io.sp.common.model.ReadingsBatch
import pl.edu.agh.eaiib.io.sp.common.model.ReadingsBatchDecoder
import pl.edu.agh.eaiib.io.sp.service.ReadingsService

@RestController
@RequestMapping("/api/readingsBatch")
class ReadingsBatchRestController(private val readingsService: ReadingsService) {

    @PostMapping
    fun addReadingsBatch(@RequestBody readingsBatch: ReadingsBatch): ResponseEntity<Collection<Reading>> {
        val readings = ReadingsBatchDecoder.decode(readingsBatch.deviceId, readingsBatch.values)
        val added = readingsService.addAll(readings)

        return ResponseEntity(added, HttpStatus.OK)
    }

}