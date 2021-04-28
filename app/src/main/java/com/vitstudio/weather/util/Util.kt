package com.vitstudio.weather.util

import com.vitstudio.weather.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun launchIo(task: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        task()
    }
}

fun Double.convectorTempFaringateToCelsius(): String =
    this.minus(273.15).toInt().toString() + "\u00b0" + "C"

var APP_ACTIVITY = MainActivity()
