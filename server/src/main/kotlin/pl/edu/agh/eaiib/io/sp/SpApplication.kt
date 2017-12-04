package pl.edu.agh.eaiib.io.sp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpApplication

fun main(args: Array<String>) {
    runApplication<SpApplication>(*args)
}
