package com.vitstudio.weather.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherDbEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val name: String?,
    val icon: String?,
    val description: String?,
    val temp: String?,
    val tempMin: String?,
    val humidity: Int?,
    val pressure: Int?,
    val tempMax: String?,
    val tempFeelsLike: String?,
)
