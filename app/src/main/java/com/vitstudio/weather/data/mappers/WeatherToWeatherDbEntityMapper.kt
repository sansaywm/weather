package com.vitstudio.weather.data.mappers

import com.vitstudio.weather.data.db.entity.WeatherDbEntity
import com.vitstudio.weather.data.entity.Weather
import javax.inject.Inject

class WeatherToWeatherDbEntityMapper @Inject constructor() {
    fun map(from: Weather?) = WeatherDbEntity(
        id = from?.id ?: 0,
        name = from?.name.orEmpty(),
        icon = from?.icon.orEmpty(),
        description = from?.description.orEmpty(),
        temp = from?.temp.orEmpty(),
        tempMin = from?.tempMin.orEmpty(),
        tempFeelsLike = from?.tempFeelsLike.orEmpty(),
        tempMax = from?.tempMax.orEmpty(),
        humidity = from?.humidity ?: 0,
        pressure = from?.pressure ?: 0
    )
}