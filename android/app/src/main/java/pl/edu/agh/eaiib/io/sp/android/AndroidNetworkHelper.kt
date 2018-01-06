package pl.edu.agh.eaiib.io.sp.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import java.util.concurrent.CopyOnWriteArrayList

class AndroidNetworkHelper(private val context: Context) {
    private val androidNetworkAvailabilityListener = AndroidNetworkAvailabilityListener(this, context)

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected ?: false
    }

    fun addNetworkStateListener(listener: NetworkAvailabilityListener) {
        androidNetworkAvailabilityListener.addListener(listener)
    }
}

private class AndroidNetworkAvailabilityListener(private val networkHelper: AndroidNetworkHelper,
                                                 context: Context) {

    private val listeners = CopyOnWriteArrayList<NetworkAvailabilityListener>()

    init {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                notifyListeners()
            }
        }

        val filter = IntentFilter()
        filter.addAction(ANDROID_CONNECTIVITY_CHANGE_ACTION)
        filter.addAction(ANDROID_WIFI_CHANGE_ACTION)

        context.registerReceiver(receiver, filter)
    }

    fun addListener(listener: NetworkAvailabilityListener) {
        listeners.add(listener)
    }

    private fun notifyListeners() {
        val networkAvailable = networkHelper.isNetworkAvailable()
        listeners.forEach {
            it.networkAvailabilityChanged(networkAvailable)
        }
    }
}

interface NetworkAvailabilityListener {
    fun networkAvailabilityChanged(networkAvailable: Boolean)
}

private const val ANDROID_CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
private const val ANDROID_WIFI_CHANGE_ACTION = "android.net.wifi.WIFI_STATE_CHANGED"