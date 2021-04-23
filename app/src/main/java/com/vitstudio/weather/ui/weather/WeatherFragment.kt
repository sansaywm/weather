package com.vitstudio.weather.ui.weather

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.vitstudio.weather.R
import com.vitstudio.weather.data.entity.LocationModel
import com.vitstudio.weather.databinding.FragmentWeatherBinding
import com.vitstudio.weather.util.*
import java.util.concurrent.TimeUnit

class WeatherFragment : Fragment(), LocListenerInterface {
    private var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding
        get() = _binding!!
    private lateinit var viewModel: WeatherViewModel
    private lateinit var mToolbar: Toolbar
    private lateinit var locationManager: LocationManager
    private lateinit var myLocationListener: MyLocationListener
    lateinit var permissionHelper: PermissionHelper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mToolbar = APP_ACTIVITY.mToolbar
        permissionHelper = PermissionHelper(requireContext())
        initButtonAdd()
        initLocation()
        loadWeather()

    }

    override fun onStart() {
        super.onStart()
        visibilityToolbar()
        APP_ACTIVITY.buttonBack.enableDrawer()
        when (startAppCount(requireContext())) {
            1 -> {
                setOneTimeWorkRequest()
                permissionHelper.permissionAutoStartInit()
            }
            2 -> {
                permissionHelper.permissionPowerSavingInit()
            }
        }
    }

    private fun initButtonAdd() {
        binding.buttonAdd.setOnClickListener {
            val nav = findNavController()
            nav.navigate(R.id.searchWeatherFragment)
        }
    }

    private fun initLocation() {
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        myLocationListener = MyLocationListener().also { it.locListenerInterface = this }
        checkPermissionLocation()
    }

    private fun visibilityToolbar() {
        mToolbar.visibility = View.GONE
        binding.mainToolbar.visibility = View.GONE
    }

    private fun loadWeather() {
        viewModel.location.observe(viewLifecycleOwner, {
            viewModel.loadWeather(it)
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        checkPermissionLocation()
    }

    private fun checkPermissionLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION), 100)
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1,
                10f, myLocationListener)
        }
    }

    override fun getLocation(location: Location) {
        viewModel.setLocation(LocationModel(location.longitude, location.latitude))
    }

    private fun setOneTimeWorkRequest() {
        val workManager = WorkManager.getInstance(requireContext())
        val constraints = Constraints.Builder()
            .build()
        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(WorkManagerHelper::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
        workManager.enqueue(periodicWorkRequest)
    }
}