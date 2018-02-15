package pl.edu.agh.eaiib.io.sp.rest.readings

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.edu.agh.eaiib.io.sp.common.model.Reading
import pl.edu.agh.eaiib.io.sp.service.ReadingsService

@RestController
@RequestMapping("/api/readings")
class ReadingsRestController(private val readingsService: ReadingsService) {

    @PostMapping
    fun addReadings(@RequestBody readings: List<Reading>): ResponseEntity<List<Reading>> {
        val added = readings.asSequence()
                .map { readingsService.add(it) }
                .toList()

        return ResponseEntity(added, HttpStatus.OK)
    }

    @GetMapping
    fun getAllReadings(@RequestParam(required = false) limit: Int?): List<Reading> {

        if (limit != null && limit > 0) {
            return readingsService.getAll().takeLast(limit)
        }

        return readingsService.getAll()
    }
}

