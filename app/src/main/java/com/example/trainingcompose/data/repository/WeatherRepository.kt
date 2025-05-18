package com.example.trainingcompose.data.repository

import com.example.trainingcompose.domain.entity.WeatherEntity

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): Result<WeatherEntity>
}