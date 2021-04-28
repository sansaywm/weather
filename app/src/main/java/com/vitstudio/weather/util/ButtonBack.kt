package com.vitstudio.weather.util

import androidx.appcompat.widget.Toolbar
import com.vitstudio.weather.MainActivity

class ButtonBack(private val mainActivity: MainActivity, private val toolbar: Toolbar) {

    fun disableDrawer() {
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            mainActivity.supportFragmentManager.popBackStack()
        }
    }

    fun enableDrawer() {
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}