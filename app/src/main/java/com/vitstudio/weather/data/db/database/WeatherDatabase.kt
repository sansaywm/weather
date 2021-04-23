package com.vitstudio.weather.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vitstudio.weather.data.db.dao.WeatherDao
import com.vitstudio.weather.data.db.entity.WeatherDbEntity

@Database(entities = [WeatherDbEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getDao(): WeatherDao
}

object DatabaseProvider {
    fun provide(context: Context) =
        Room.databaseBuilder(context, WeatherDatabase::class.java, "WeatherDatabase").build()

}