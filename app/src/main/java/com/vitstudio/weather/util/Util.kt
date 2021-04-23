package com.vitstudio.weather.util

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.vitstudio.weather.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

fun launchIo(task: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        task()
    }
}

fun launchUi(task: suspend () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        task()
    }
}

fun Double.convectorTempFaringateToCelsius(): String =
    this.minus(273.15).toInt().toString() + "\u00b0" + "C"

var APP_ACTIVITY = MainActivity()

fun startAppCount(context: Context): Int {
    val pref = context.getSharedPreferences("Weather", AppCompatActivity.MODE_PRIVATE)
    var count = pref.getInt("enter_app", 0)
    val edit = pref.edit()
    count += 1
    edit.putInt("enter_app", count)
    edit.apply()
    return count
}