package pl.edu.agh.eaiib.io.sp.android

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import pl.edu.agh.eaiib.io.sp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, SensorService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, SensorService::class.java))
    }
}
