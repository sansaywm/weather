package com.vitstudio.weather.ui.weather

import android.app.Application
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vitstudio.weather.data.db.database.DatabaseProvider
import com.vitstudio.weather.data.entity.LocationModel
import com.vitstudio.weather.data.entity.Weather
import com.vitstudio.weather.domain.*
import com.vitstudio.weather.util.launchIo
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherViewModel
@Inject constructor(
    private val app: Application,
) : AndroidViewModel(app) {
    private val loadWeathersFromDatabaseUseCase = LoadWeathersFromDatabaseUseCase()
    private val saveWeatherToDatabaseUseCase = SaveWeatherToDatabaseUseCase()
    private val updateWeathersDatabaseUseCase = UpdateWeatherDatabaseUseCase()
    private val useCase = LoadWeatherLocationUseCase()

    private val dao = DatabaseProvider.provide(app.applicationContext).getDao()

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private val _location = MutableLiveData<LocationModel>()
    val location: LiveData<LocationModel>
        get() = _location

    init {
        launchIo {
            if (loadWeathersFromDatabase().isNotEmpty())
                _weather.postValue(loadWeathersFromDatabase()[0])
        }
    }

    fun setLocation(locationModel: LocationModel) {
        _location.postValue(locationModel)
    }

    fun loadWeather(locationModel: LocationModel?) {

        launchIo {
            if (isNetworkConnected() && locationModel != null) {
                val saveWeatherFromUseCase =
                    useCase.doWork(LoadWeatherLocationUseCase.Params(locationModel.longitude,
                        locationModel.latitude))
                _weather.postValue(saveWeatherFromUseCase)
                if (checkWeatherLocationInDataBase()) {
                    saveWeatherFromUseCase.id = 1L
                    updateWeathersDatabaseUseCase.doWork(UpdateWeatherDatabaseUseCase.Params(dao,
                        saveWeatherFromUseCase))
                } else {
                    saveWeatherToDatabaseUseCase.doWork(SaveWeatherToDatabaseUseCase.Params(
                        dao,
                        saveWeatherFromUseCase))
                }
            }
        }
    }

    private suspend fun loadWeathersFromDatabase(): List<Weather> = suspendCoroutine { coroutine ->
        launchIo {
            val list =
                loadWeathersFromDatabaseUseCase.doWork(
                    LoadWeathersFromDatabaseUseCase.Params(dao)
                )
            coroutine.resume(list)
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm =
            ContextCompat.getSystemService(app.applicationContext, ConnectivityManager::class.java)
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    private suspend fun checkWeatherLocationInDataBase(): Boolean =
        suspendCoroutine { coroutine ->
            launchIo {
                if (loadWeathersFromDatabase().filter { it.id == 1L }.size == 1) {
                    coroutine.resume(true)
                } else {
                    coroutine.resume(false)
                }
            }
        }
}