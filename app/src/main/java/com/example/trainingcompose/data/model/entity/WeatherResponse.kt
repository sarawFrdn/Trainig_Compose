package com.example.trainingcompose.data.model.entity

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current")
    val current: CurrentWeather
) {
    data class CurrentWeather(
        @SerializedName("temperature_2m")
        val temperature: Float,
        @SerializedName("relative_humidity_2m")
        val humidity: Int,
        @SerializedName("wind_speed_10m")
        val windSpeed: Float
    )
}