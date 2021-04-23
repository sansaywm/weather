package com.vitstudio.weather.domain

import com.vitstudio.weather.data.entity.Weather
import com.vitstudio.weather.di.components.DaggerSharedComponent
import com.vitstudio.weather.repository.WeatherRepository
import javax.inject.Inject

class LoadWeatherCityUseCase {

    @Inject
    lateinit var weatherRepository: WeatherRepository

    init {
        DaggerSharedComponent.create().initializeLoadWeatherCityUseCase(this)
    }

    suspend fun doWork(params: Params): Weather? =
        weatherRepository.getWeatherCityName(params.city)

    data class Params(val city: String)
}