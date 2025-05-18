package com.example.trainingcompose.data.repository


import com.example.trainingcompose.data.remote.WeatherApi
import com.example.trainingcompose.domain.entity.WeatherEntity

class WeatherRepositoryImpl(private val api: WeatherApi) : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double): Result<WeatherEntity> {
        val response = api.getWeather(latitude, longitude)
        return Result.success(
            WeatherEntity(
                temperature = response.current.temperature,
                humidity = response.current.humidity,
                windSpeed = response.current.windSpeed
            )
        )
    }
}