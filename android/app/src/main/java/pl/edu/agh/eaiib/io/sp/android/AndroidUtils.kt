package pl.edu.agh.eaiib.io.sp.android

import android.content.Context
import android.provider.Settings.Secure

fun getDeviceID(context: Context): String {
    return Secure.getString(context.contentResolver, Secure.ANDROID_ID)
}