package com.vitstudio.weather.data.dto


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("base")
    val base: String = "",
    @SerializedName("cod")
    val cod: Int = 0,
    @SerializedName("dt")
    val dt: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("main")
    val main: Main = Main(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("sys")
    val sys: Sys = Sys(),
    @SerializedName("timezone")
    val timezone: Int = 0,
    @SerializedName("visibility")
    val visibility: Int = 0,
    @SerializedName("weather")
    val weather: List<Weather> = listOf(),
    ) {

    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double = 0.0,
        @SerializedName("humidity")
        val humidity: Int = 0,
        @SerializedName("pressure")
        val pressure: Int = 0,
        @SerializedName("temp")
        val temp: Double = 0.0,
        @SerializedName("temp_max")
        val tempMax: Double = 0.0,
        @SerializedName("temp_min")
        val tempMin: Double = 0.0
    )

    data class Sys(
        @SerializedName("country")
        val country: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("sunrise")
        val sunrise: Int = 0,
        @SerializedName("sunset")
        val sunset: Int = 0,
        @SerializedName("type")
        val type: Int = 0
    )

    data class Weather(
        @SerializedName("description")
        val description: String = "",
        @SerializedName("icon")
        val icon: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("main")
        val main: String = ""
    )
}