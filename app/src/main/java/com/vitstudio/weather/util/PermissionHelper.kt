package com.vitstudio.weather.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.thelittlefireman.appkillermanager.managers.KillerManager
import com.vitstudio.weather.R

class PermissionHelper(private val context: Context) {

    fun permissionAutoStartInit() {
        MaterialDialog(context)
            .show {
                title(R.string.title_permission_message)
                message(R.string.permission_message)
                    .positiveButton {
                        try {
                            val intent = Intent()
                            KillerManager.doActionAutoStart(context)
                            val list: List<ResolveInfo> = context.packageManager
                                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                            if (list.isNotEmpty()) {
                                context.startActivity(intent)
                            }
                        } catch (e: Exception) {
                            Log.e("weather", e.message.toString())
                        }
                    }
            }
    }

    fun permissionPowerSavingInit() {
        MaterialDialog(context)
            .show {
                title(R.string.title_power_saving)
                message(R.string.power_saving)
                    .positiveButton {
                        try {
                            val intent = Intent()
                            KillerManager.doActionPowerSaving(context)
                            val list: List<ResolveInfo> = context.packageManager
                                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                            if (list.isNotEmpty()) {
                                context.startActivity(intent)
                            }
                        } catch (e: Exception) {
                            Log.e("weather", e.message.toString())
                        }
                    }
            }
    }

    inline fun checkPermissions(context: Context, block: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            APP_ACTIVITY.requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION), 100)
        } else {
            block()
        }
    }
}