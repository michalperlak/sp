package pl.edu.agh.eaiib.io.sp.android

import android.content.BroadcastReceiver
import android.content.Context
import pl.edu.agh.eaiib.io.sp.SensorService
import android.content.Intent

class RestartServiceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.startService(Intent(context, SensorService::class.java))
    }
}

const val RESTART_SERVICE_ACTION_NAME = "pl.edu.agh.eaiib.io.sp.android.RestartService"