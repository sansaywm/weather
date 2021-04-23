package com.vitstudio.weather.util

import android.location.Location

interface LocListenerInterface {
    fun getLocation(location: Location)
}