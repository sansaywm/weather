package com.vitstudio.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.vitstudio.weather.databinding.ActivityMainBinding
import com.vitstudio.weather.util.*

class MainActivity : AppCompatActivity() {
    lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!
    lateinit var buttonBack: ButtonBack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mToolbar = binding.mainToolbar
        setSupportActionBar(mToolbar)
        buttonBack = ButtonBack(this, mToolbar)
        APP_ACTIVITY = this
    }
}
