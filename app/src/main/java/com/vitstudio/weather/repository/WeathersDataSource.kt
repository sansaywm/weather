package com.vitstudio.weather.repository

import com.vitstudio.weather.data.db.dao.WeatherDao
import com.vitstudio.weather.data.db.entity.WeatherDbEntity
import com.vitstudio.weather.data.dto.WeatherResponse
import com.vitstudio.weather.network.WeatherApi
import retrofit2.Response
import javax.inject.Inject

class WeathersDataSource @Inject constructor(private val api: WeatherApi) {

    suspend fun insertWeatherDatabase(dao: WeatherDao, weather: WeatherDbEntity) {
        dao.insert(weather)
    }

    suspend fun updateWeatherDatabase(dao: WeatherDao, weather: WeatherDbEntity) {
        dao.updateWeather(weather)
    }

    suspend fun deleteWeatherDatabase(dao: WeatherDao, weather: String) {
        dao.deleteWeather(weather)
    }

    suspend fun getWeatherCityName(city: String): Response<WeatherResponse> =
        api.getWeatherCityNameAsync(city)

    suspend fun getWeatherLocation(lon: Double, lat: Double): Response<WeatherResponse> =
        api.getWeatherLocationAsync(lon, lat)

}