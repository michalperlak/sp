package pl.edu.agh.eaiib.io.sp.data.publish

import io.reactivex.Flowable
import pl.edu.agh.eaiib.io.sp.common.model.Reading
import pl.edu.agh.eaiib.io.sp.common.model.ReadingsBatch
import pl.edu.agh.eaiib.io.sp.common.model.ReadingsBatchEncoder
import pl.edu.agh.eaiib.io.sp.config.Configuration
import pl.edu.agh.eaiib.io.sp.rest.AbstractPublisher
import pl.edu.agh.eaiib.io.sp.rest.Api
import java.util.concurrent.ConcurrentLinkedQueue

class SensorDataPublisher(config: Configuration) : AbstractPublisher<Reading>(config) {
    private val elementsToPublish = ConcurrentLinkedQueue<Reading>()
    private val lastPublishMillis = System.currentTimeMillis()

    override fun publish(api: Api, data: Reading): Flowable<Any> {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastPublishMillis < MIN_INTERVAL) {
            elementsToPublish.add(data)
        }

        val elements = elementsToPublish.toList()
        elementsToPublish.clear()

        val readingsBatch = ReadingsBatch(Configuration.deviceId, ReadingsBatchEncoder.encode(elements))
        return api.addReadingsBatch(readingsBatch)
    }
}

private const val MIN_INTERVAL = 30 * 1000