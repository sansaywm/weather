package com.vitstudio.weather.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vitstudio.weather.data.db.database.DatabaseProvider
import com.vitstudio.weather.data.entity.Weather
import com.vitstudio.weather.domain.DeleteWeatherDatabaseUseCase
import com.vitstudio.weather.domain.LoadWeatherCityUseCase
import com.vitstudio.weather.domain.LoadWeathersFromDatabaseUseCase
import com.vitstudio.weather.util.launchIo

class SearchWeatherViewModel(app: Application) : AndroidViewModel(app) {
    private val loadWeathersFromDatabaseUseCase = LoadWeathersFromDatabaseUseCase()
    private val loadWeatherCityUseCase = LoadWeatherCityUseCase()
    private val deleteWeatherDatabaseUseCase = DeleteWeatherDatabaseUseCase()
    private val dao = DatabaseProvider.provide(app.applicationContext).getDao()

    private val _weatherList = MutableLiveData<ArrayList<Weather>>()
    val weatherList: LiveData<ArrayList<Weather>>
        get() = _weatherList

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather


    fun loadWeathersFromDatabase() {
        launchIo {
            val list =
                loadWeathersFromDatabaseUseCase.doWork(
                    LoadWeathersFromDatabaseUseCase.Params(
                        dao
                    )
                )
            _weatherList.postValue(list as ArrayList<Weather>)
        }
    }

    fun loadWeatherCityName(city: String) {
        launchIo {
            val weather = loadWeatherCityUseCase.doWork(LoadWeatherCityUseCase.Params(city))
            _weather.postValue(weather)
        }
    }

    fun deleteWhetherDatabase(weather: String) {
        launchIo {
            deleteWeatherDatabaseUseCase.doWork(DeleteWeatherDatabaseUseCase.Params(dao, weather))
        }
    }
}