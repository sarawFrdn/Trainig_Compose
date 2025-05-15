package com.example.trainingcompose.data.repository


import com.example.trainingcompose.data.model.entity.WeatherEntity
import com.example.trainingcompose.data.remote.WeatherApi

class WeatherRepositoryImpl(private val api: WeatherApi) : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherEntity {
        val response = api.getWeather(latitude, longitude)
        return WeatherEntity(
            temperature = response.current.temperature,
            humidity = response.current.humidity,
            windSpeed = response.current.windSpeed
        )
    }
}