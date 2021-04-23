package com.vitstudio.weather.network

import com.vitstudio.weather.data.dto.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_KEY = "066684c224288ec83f079c8017eb1057"

interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherCityNameAsync(
        @Query("q")
        cityName: String,
        @Query("appid")
        appid: String = BASE_KEY,
        @Query("lang")
        lang: String = "ru",
    ): Response<WeatherResponse>

    @GET("weather")
    suspend fun getWeatherLocationAsync(
        @Query("lon")
        lon: Double,
        @Query("lat")
        lat: Double,
        @Query("appid")
        appid: String = BASE_KEY,
        @Query("lang")
        lang: String = "ru",
    ): Response<WeatherResponse>
}
