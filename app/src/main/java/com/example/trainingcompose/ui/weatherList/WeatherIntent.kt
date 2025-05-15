package com.example.trainingcompose.ui.weatherList

sealed class WeatherIntent {
    data class FetchWeather(val latitude: String, val longitude: String) : WeatherIntent()
}