package com.example.trainingcompose.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.open-meteo.com/"

    val weatherApi: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

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
}