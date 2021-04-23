package com.vitstudio.weather.domain

import com.vitstudio.weather.data.entity.Weather
import com.vitstudio.weather.di.components.DaggerSharedComponent
import com.vitstudio.weather.repository.WeatherRepository
import javax.inject.Inject

class LoadWeatherLocationUseCase {

    @Inject
    lateinit var weatherRepository: WeatherRepository

    init {
        DaggerSharedComponent.create().initializeLoadWeatherLocationUseCase(this)
    }

    suspend fun doWork(params: Params): Weather =
        weatherRepository.getWeatherLocation(params.lon, params.lat)

    data class Params(val lon: Double, val lat: Double)
}