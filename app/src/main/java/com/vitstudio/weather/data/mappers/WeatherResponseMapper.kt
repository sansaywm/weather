package com.vitstudio.weather.data.mappers

import com.vitstudio.weather.data.dto.WeatherResponse
import com.vitstudio.weather.data.entity.Weather
import com.vitstudio.weather.util.convectorTempFaringateToCelsius
import javax.inject.Inject

class WeatherResponseMapper @Inject constructor() {
    fun map(from: WeatherResponse?) = Weather(
        name = from?.name.orEmpty(),
        icon = "https://openweathermap.org/img/wn/${from?.weather?.get(0)?.icon.orEmpty()}@2x.png",
        description = from?.weather?.get(0)?.description.orEmpty(),
        temp = from?.main?.temp?.convectorTempFaringateToCelsius(),
        tempMin = from?.main?.tempMin?.convectorTempFaringateToCelsius(),
        tempMax = from?.main?.tempMax?.convectorTempFaringateToCelsius(),
        tempFeelsLike = from?.main?.feelsLike?.convectorTempFaringateToCelsius(),
        humidity = from?.main?.humidity ?: 0,
        pressure = from?.main?.pressure ?: 0
    )
}