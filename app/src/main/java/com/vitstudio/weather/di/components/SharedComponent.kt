package com.vitstudio.weather.di.components

import com.vitstudio.weather.di.modules.SharedModule
import com.vitstudio.weather.domain.*
import dagger.Component

@Component(modules = [SharedModule::class])
interface SharedComponent {
    fun initializeLoadWeatherCityUseCase(
        loadWeatherListUseCase: LoadWeatherCityUseCase,
    )

    fun initializeLoadWeatherLocationUseCase(
        loadWeatherListUseCase: LoadWeatherLocationUseCase,
    )

    fun initializeUpdateWeatherUseCase(
        updateWeatherListUseCase: UpdateWeatherDatabaseUseCase,
    )

    fun initializeDeleteWeatherUseCase(
        deleteWeatherListUseCase: DeleteWeatherDatabaseUseCase,
    )

    fun initializeSaveWeatherToDatabaseUseCase(
        saveWeatherToDatabaseUseCase: SaveWeatherToDatabaseUseCase,
    )

    fun initializeLoadWeathersFromDatabaseUseCase(
        loadWeathersFromDatabaseUseCase: LoadWeathersFromDatabaseUseCase,
    )


}