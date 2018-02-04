package pl.edu.agh.eaiib.io.sp.android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pl.edu.agh.eaiib.io.sp.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestLocationPermission()
        startService(Intent(this, ServicesAggregate::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, ServicesAggregate::class.java))
    }

    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }

        if (PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            return
        }

        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), INITIAL_REQUEST)
    }
}

private const val INITIAL_REQUEST = 1337
