package pl.edu.agh.eaiib.io.sp.android

import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.jetbrains.anko.sensorManager
import pl.edu.agh.eaiib.io.sp.SensorDataService
import pl.edu.agh.eaiib.io.sp.ServicesUtil
import pl.edu.agh.eaiib.io.sp.config.Configuration

class SensorService : Service() {
    private var androidNetworkHelper: AndroidNetworkHelper? = null
    private var sensorDataService: SensorDataService? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Configuration.deviceId = getDeviceID(this)
        registerAndroidHelper()
        registerSensorDataService()
        sensorDataService?.startCollectingData()
        sensorDataService?.startPublishingData()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorDataService?.stopCollectingData()
        sensorDataService?.stopPublishingData()

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

        ServicesUtil.unregisterService(androidNetworkHelper!!)
    }

    private fun registerSensorDataService() {
        val sensorDataService = SensorDataService(sensorManager, Configuration)
        ServicesUtil.registerService(sensorDataService)
        this.sensorDataService = sensorDataService
    }

    private fun unregisterSensorDataService() {
        if (sensorDataService == null) {
            return
        }

        ServicesUtil.unregisterService(sensorDataService!!)
    }
}