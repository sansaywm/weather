package com.vitstudio.weather.ui.weatheradd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vitstudio.weather.R
import com.vitstudio.weather.databinding.FragmentWeatheraddBinding
import com.vitstudio.weather.util.APP_ACTIVITY
import com.vitstudio.weather.util.KEY_ADD_NEW
import com.vitstudio.weather.util.KEY_CITY_ID
import com.vitstudio.weather.util.KEY_CITY_NAME

class WeatherAddFragment : Fragment() {
    private var _binding: FragmentWeatheraddBinding? = null
    private val binding: FragmentWeatheraddBinding
        get() = _binding!!

    private lateinit var viewModel: WeatherAddViewModel
    lateinit var mToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = ViewModelProvider(this).get(WeatherAddViewModel::class.java)
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_weatheradd, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mToolbar = APP_ACTIVITY.mToolbar.also { it.visibility = View.VISIBLE }
        initButtonAdd()
        viewModel.loadWeatherCityName(arguments?.getString(KEY_CITY_NAME)!!)
    }

    private fun initButtonAdd() {
        if (arguments?.getBoolean(KEY_ADD_NEW)!!) {
            binding.buttonAddUsersGroup.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    val id = arguments?.getLong(KEY_CITY_ID)
                    viewModel.addWeatherToDatabase(id!!)
                    val nav = findNavController()
                    nav.popBackStack(R.id.weatherAddFragment, true)
                    nav.navigate(R.id.searchWeatherFragment)
                }
            }
        } else {
            binding.buttonAddUsersGroup.visibility = View.GONE
        }
    }
}