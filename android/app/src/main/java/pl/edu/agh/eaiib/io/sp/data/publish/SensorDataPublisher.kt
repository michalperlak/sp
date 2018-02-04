package pl.edu.agh.eaiib.io.sp.data.publish

import io.reactivex.Flowable
import pl.edu.agh.eaiib.io.sp.common.model.Reading
import pl.edu.agh.eaiib.io.sp.config.Configuration
import pl.edu.agh.eaiib.io.sp.rest.AbstractPublisher
import pl.edu.agh.eaiib.io.sp.rest.Api

class SensorDataPublisher(config: Configuration) : AbstractPublisher<Reading>(config) {
    override fun publish(api: Api, data: Reading): Flowable<Any> = api.addReading(data)
}