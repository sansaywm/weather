package com.vitstudio.weather.di.modules

import com.vitstudio.weather.network.RetrofitProvider
import com.vitstudio.weather.network.WeatherApi
import dagger.Module
import dagger.Provides

@Module
class SharedModule {
    @Provides
    fun provideWeatherApi(): WeatherApi {
        return RetrofitProvider().provideApi()
    }
}