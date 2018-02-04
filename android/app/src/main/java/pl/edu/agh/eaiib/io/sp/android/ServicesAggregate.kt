package pl.edu.agh.eaiib.io.sp.android

import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.jetbrains.anko.locationManager
import org.jetbrains.anko.sensorManager
import pl.edu.agh.eaiib.io.sp.config.Configuration
import pl.edu.agh.eaiib.io.sp.services.CommentsService
import pl.edu.agh.eaiib.io.sp.services.SensorDataService
import pl.edu.agh.eaiib.io.sp.services.ServicesUtil

class ServicesAggregate : Service() {
    private var androidNetworkHelper: AndroidNetworkHelper? = null
    private var sensorDataService: SensorDataService? = null
    private var commentsService: CommentsService? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Configuration.deviceId = getDeviceID(this)
        registerAndroidHelper()
        registerSensorDataService()
        sensorDataService?.startCollectingData()
        sensorDataService?.startPublishingData()

        registerCommentsService()
        commentsService?.startListening()
        commentsService?.startPublishing()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorDataService?.stopCollectingData()
        sensorDataService?.stopPublishingData()

        commentsService?.stopListening()
        commentsService?.stopPublishing()

        unregisterCommentsService()
        unregisterSensorDataService()
        unregisterAndroidHelper()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun registerAndroidHelper() {
        val androidNetworkHelper = AndroidNetworkHelper(this)
        ServicesUtil.registerService(androidNetworkHelper)
        this.androidNetworkHelper = androidNetworkHelper
    }

    private fun unregisterAndroidHelper() {
        if (androidNetworkHelper == null) {
            return
        }

        androidNetworkHelper!!.unregister()
        ServicesUtil.unregisterService(androidNetworkHelper!!)
    }

    private fun registerSensorDataService() {
        val sensorDataService = SensorDataService(sensorManager, locationManager, Configuration)
        ServicesUtil.registerService(sensorDataService)
        this.sensorDataService = sensorDataService
    }

    private fun unregisterSensorDataService() {
        if (sensorDataService == null) {
            return
        }

        ServicesUtil.unregisterService(sensorDataService!!)
    }

    private fun registerCommentsService() {
        val commentsService = CommentsService(this, locationManager, Configuration)
        ServicesUtil.registerService(commentsService)
        this.commentsService = commentsService
    }

    private fun unregisterCommentsService() {
        if (commentsService == null) {
            return
        }

        ServicesUtil.unregisterService(commentsService!!)
    }
}