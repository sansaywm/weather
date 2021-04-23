package com.vitstudio.weather.util

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class MyLocationListener() : LocationListener {
    lateinit var locListenerInterface: LocListenerInterface

    override fun onLocationChanged(location: Location?) {
        this.locListenerInterface.getLocation(location!!)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {


    }
}