package com.example.trainingcompose.data.repository

import com.example.trainingcompose.data.model.entity.WeatherEntity

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): WeatherEntity
}