package pl.edu.agh.eaiib.io.sp.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class SensorChangedEventHandlerAdapter(private val sensorType: Int,
                                       private val handler: (SensorEvent) -> Unit) : SensorEventListener {

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        if (sensorType == sensorEvent.sensor.type) {
            handler.invoke(sensorEvent)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}
