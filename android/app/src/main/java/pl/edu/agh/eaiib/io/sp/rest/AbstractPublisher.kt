package pl.edu.agh.eaiib.io.sp.rest

import io.reactivex.Flowable
import pl.edu.agh.eaiib.io.sp.services.ServicesUtil
import pl.edu.agh.eaiib.io.sp.android.AndroidNetworkHelper
import pl.edu.agh.eaiib.io.sp.android.NetworkAvailabilityListener
import pl.edu.agh.eaiib.io.sp.config.Configuration
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executor
import java.util.concurrent.Executors

abstract class AbstractPublisher<in T>(config: Configuration) : NetworkAvailabilityListener {
    private val api = Api.create(config.serverBaseUrl)
    private val publishQueue = ConcurrentLinkedQueue<T>()

    private val executor: Executor = Executors.newSingleThreadExecutor { runnable ->
        val thread = Thread(runnable)
        thread.isDaemon = true
        thread
    }

    private var publishEnabled = true
    private var networkAvailable = ServicesUtil.getService(AndroidNetworkHelper::class.java).isNetworkAvailable()

    init {
        executor.execute({
            while (true) {
                tryPublishData()
            }
        })
    }

    fun startPublishingData() {
        publishEnabled = true
    }

    fun stopPublishingData() {
        publishEnabled = false
    }

    fun addToQueue(element: T) {
        if (!publishEnabled) {
            return
        }

        publishQueue.add(element)
    }

    override fun networkAvailabilityChanged(networkAvailable: Boolean) {
        this.networkAvailable = networkAvailable
    }

    private fun tryPublishData() {
        if (!networkAvailable || publishQueue.isEmpty()) {
            return
        }

        val data = publishQueue.poll()
        val result = publish(api, data)
        result.subscribe()
    }

    abstract fun publish(api: Api, data: T): Flowable<Any>
}