package com.vitstudio.weather.data.entity

data class Weather(
    val name: String?,
    val icon: String?,
    val description: String?,
    val temp: String?,
    val tempMin: String?,
    val humidity: Int?,
    val pressure: Int?,
    val tempMax: String?,
    val tempFeelsLike: String?,
    var id: Long? = 0,
)
