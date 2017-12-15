package pl.edu.agh.eaiib.io.sp.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import pl.edu.agh.eaiib.io.sp.data.SensorEventHandler

class AndroidSensorEventHandlerAdapter(private val sensorType: Int,
                                       private val handler: SensorEventHandler) : SensorEventListener {

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        if (sensorType == sensorEvent.sensor.type) {
            handler.handle(sensorEvent)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}
