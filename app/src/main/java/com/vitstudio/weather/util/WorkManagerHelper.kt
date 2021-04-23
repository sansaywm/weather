package com.vitstudio.weather.util

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.vitstudio.weather.data.db.database.DatabaseProvider
import com.vitstudio.weather.data.entity.LocationModel
import com.vitstudio.weather.data.entity.Weather
import com.vitstudio.weather.domain.LoadWeatherCityUseCase
import com.vitstudio.weather.domain.LoadWeathersFromDatabaseUseCase
import com.vitstudio.weather.domain.UpdateWeatherDatabaseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WorkManagerHelper(private val context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    private val updateWeathersDatabaseUseCase = UpdateWeatherDatabaseUseCase()
    private val dao = DatabaseProvider.provide(context).getDao()
    private val loadWeathersFromDatabaseUseCase = LoadWeathersFromDatabaseUseCase()
    private val loadWeatherCityUseCase = LoadWeatherCityUseCase()

    override fun doWork(): Result {
        return try {
            CoroutineScope(Job() + Dispatchers.IO).launch {
                Log.e("work", "start")
                val weatherList = loadWeathersFromDatabase()
                Log.e("work", weatherList.toString())
                if (weatherList.isNotEmpty() && weatherList[0].name != null) {
                    val weather = loadWeatherCityName(weatherList[0].name!!)
                    if (weather != null) {
                        weather.id = 0
                        updateWeathersDatabaseUseCase.doWork(
                            UpdateWeatherDatabaseUseCase.Params(dao, weather))
                        Log.e("work", "step2")
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun loadWeathersFromDatabase(): List<Weather> = suspendCoroutine { coroutine ->
        launchIo {
            val list =
                loadWeathersFromDatabaseUseCase.doWork(
                    LoadWeathersFromDatabaseUseCase.Params(dao)
                )
            coroutine.resume(list)
        }
    }

    private suspend fun loadWeatherCityName(city: String): Weather? =
        suspendCoroutine { coroutine ->
            launchIo {
                val weather = loadWeatherCityUseCase.doWork(LoadWeatherCityUseCase.Params(city))
                coroutine.resume(weather)
            }
        }
 }