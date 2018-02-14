package pl.edu.agh.eaiib.io.sp.service

import org.springframework.stereotype.Service
import pl.edu.agh.eaiib.io.sp.common.model.Reading
import pl.edu.agh.eaiib.io.sp.repositories.ReadingsRepository

@Service
class ReadingsService(private val repository: ReadingsRepository) {

    fun add(reading: Reading): Reading {
        return repository.save(reading)
    }

    fun addAll(readings: Collection<Reading>): Collection<Reading> {
        return repository.saveAll(readings)
    }

    fun getAll(): List<Reading> {
        return repository.findAll()
    }
}