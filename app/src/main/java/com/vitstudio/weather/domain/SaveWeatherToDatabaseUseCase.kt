package com.vitstudio.weather.domain

import com.vitstudio.weather.data.db.dao.WeatherDao
import com.vitstudio.weather.data.entity.Weather
import com.vitstudio.weather.di.components.DaggerSharedComponent
import com.vitstudio.weather.repository.WeatherRepository
import javax.inject.Inject

class SaveWeatherToDatabaseUseCase {
    @Inject
    lateinit var weatherRepository: WeatherRepository

    init {
        DaggerSharedComponent.create().initializeSaveWeatherToDatabaseUseCase(this)
    }

    suspend fun doWork(params: Params) {
        weatherRepository.insertWeatherDatabase(params.dao, params.weather)
    }

    data class Params(
        val dao: WeatherDao,
        val weather: Weather,
    )
}