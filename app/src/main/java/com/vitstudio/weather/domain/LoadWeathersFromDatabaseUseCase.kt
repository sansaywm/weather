package com.vitstudio.weather.domain

import com.vitstudio.weather.data.db.dao.WeatherDao
import com.vitstudio.weather.data.entity.Weather
import com.vitstudio.weather.di.components.DaggerSharedComponent
import com.vitstudio.weather.repository.WeatherRepository
import dagger.internal.DaggerCollections
import javax.inject.Inject

class LoadWeathersFromDatabaseUseCase {

    @Inject
    lateinit var weatherRepository: WeatherRepository

    init {
        DaggerSharedComponent.create().initializeLoadWeathersFromDatabaseUseCase(this)
    }

    suspend fun doWork(params: Params): List<Weather> =
        weatherRepository.getWeatherDataBase(params.dao)

    data class Params(val dao: WeatherDao)
}