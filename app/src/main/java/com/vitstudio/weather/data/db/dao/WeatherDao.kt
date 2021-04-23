package com.vitstudio.weather.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vitstudio.weather.data.db.entity.WeatherDbEntity
import com.vitstudio.weather.data.entity.Weather

@Dao
interface WeatherDao {

    @Insert
    suspend fun insert(weatherDbEntity: WeatherDbEntity)

    @Query("SELECT * FROM WeatherDbEntity ")
    suspend fun getAllWeather(): List<WeatherDbEntity>

    @Update
    suspend fun updateWeather(weather: WeatherDbEntity)

    @Query("DELETE FROM WeatherDbEntity WHERE name = :name")
    suspend fun deleteWeather(name: String)
}