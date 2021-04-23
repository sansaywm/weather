package com.vitstudio.weather.ui.weatheradd

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vitstudio.weather.data.db.database.DatabaseProvider
import com.vitstudio.weather.data.entity.Weather
import com.vitstudio.weather.domain.LoadWeatherCityUseCase
import com.vitstudio.weather.domain.LoadWeathersFromDatabaseUseCase
import com.vitstudio.weather.domain.SaveWeatherToDatabaseUseCase
import com.vitstudio.weather.domain.UpdateWeatherDatabaseUseCase
import com.vitstudio.weather.util.launchIo
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherAddViewModel(app: Application) : AndroidViewModel(app) {
    private val loadWeathersFromDatabaseUseCase = LoadWeathersFromDatabaseUseCase()
    private val saveWeatherToDatabaseUseCase = SaveWeatherToDatabaseUseCase()
    private val updateWeathersDatabaseUseCase = UpdateWeatherDatabaseUseCase()
    private val dao = DatabaseProvider.provide(app.applicationContext).getDao()
    private val loadWeatherCityUseCase = LoadWeatherCityUseCase()
    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    fun loadWeatherCityName(city: String) {
        launchIo {
            val weather = loadWeatherCityUseCase.doWork(LoadWeatherCityUseCase.Params(city))
            _weather.postValue(weather)
        }
    }

    fun addWeatherToDatabase(id: Long) {
        launchIo {
            val updateWeather = weather.value
            if (updateWeather != null) {
                if (checkWeatherLocationInDataBase(weather.value!!)) {
                    updateWeather.id = id
                    updateWeathersDatabaseUseCase.doWork(UpdateWeatherDatabaseUseCase.Params(dao,
                        updateWeather))
                } else {
                    saveWeatherToDatabaseUseCase.doWork(SaveWeatherToDatabaseUseCase.Params(
                        dao, updateWeather))
                }
            }
        }
    }

    private suspend fun checkWeatherLocationInDataBase(weather: Weather): Boolean =
        suspendCoroutine { coroutine ->
            launchIo {
                if (loadWeathersFromDatabase().filter { it.name == weather.name }.size == 1) {
                    coroutine.resume(true)
                } else {
                    coroutine.resume(false)
                }
            }
        }

    private suspend fun loadWeathersFromDatabase(): List<Weather> = suspendCoroutine { coroutine ->
        launchIo {
            val list =
                loadWeathersFromDatabaseUseCase.doWork(
                    LoadWeathersFromDatabaseUseCase.Params(
                        dao
                    )
                )
            coroutine.resume(list)
        }
    }
}