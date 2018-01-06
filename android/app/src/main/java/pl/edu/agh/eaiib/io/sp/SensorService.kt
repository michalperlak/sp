package pl.edu.agh.eaiib.io.sp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import pl.edu.agh.eaiib.io.sp.android.RESTART_SERVICE_ACTION_NAME

class SensorService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        //TODO register and start collecting service
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        val broadcastIntent = Intent(RESTART_SERVICE_ACTION_NAME)
        sendBroadcast(broadcastIntent)
        //TODO stop unregister collecting service
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}