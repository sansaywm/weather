package com.vitstudio.weather.repository

import com.google.gson.JsonSyntaxException
import com.vitstudio.weather.data.db.dao.WeatherDao
import com.vitstudio.weather.data.entity.Weather
import com.vitstudio.weather.data.mappers.WeatherFromWeatherDbEntityMapper
import com.vitstudio.weather.data.mappers.WeatherResponseMapper
import com.vitstudio.weather.data.mappers.WeatherToWeatherDbEntityMapper
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weathersDataSource: WeathersDataSource,
    private val weatherToWeatherDbEntityMapper: WeatherToWeatherDbEntityMapper,
    private val weatherFromWeatherDbEntityMapper: WeatherFromWeatherDbEntityMapper,
    private val weatherResponseMapper: WeatherResponseMapper,
) {

    suspend fun insertWeatherDatabase(dao: WeatherDao, weather: Weather) {
        val weatherDbEntity = weatherToWeatherDbEntityMapper.map(weather)
        weathersDataSource.insertWeatherDatabase(dao, weatherDbEntity)
    }

    suspend fun getWeatherDataBase(dao: WeatherDao): List<Weather> = dao.getAllWeather()
        .map { weatherDbEntity -> weatherFromWeatherDbEntityMapper.map(weatherDbEntity) }

    suspend fun updateWeatherDatabase(dao: WeatherDao, weather: Weather) {
        val weatherDbEntity = weatherToWeatherDbEntityMapper.map(weather)
        weathersDataSource.updateWeatherDatabase(dao, weatherDbEntity)
    }

    suspend fun deleteWeatherDatabase(dao: WeatherDao, weather: String) {
        weathersDataSource.deleteWeatherDatabase(dao, weather)
    }

    suspend fun getWeatherCityName(city: String): Weather? {
        return try {
            val response = weathersDataSource.getWeatherCityName(city)
            if (response.isSuccessful) {
                weatherResponseMapper.map(response.body())
            } else {
                null
            }
        } catch (e: JsonSyntaxException) {
            throw Throwable(e.message)
        }
    }

    suspend fun getWeatherLocation(lon: Double, lat: Double): Weather {
        val response = weathersDataSource.getWeatherLocation(lon, lat)
        if (response.isSuccessful) {
            return weatherResponseMapper.map(response.body())
        } else {
            throw Throwable(response.message())
        }
    }
}