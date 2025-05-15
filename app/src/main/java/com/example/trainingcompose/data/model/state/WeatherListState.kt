package com.example.trainingcompose.data.model.state

import com.example.trainingcompose.data.model.entity.WeatherEntity

data class WeatherState(
    val isLoading: Boolean = false,
    val weather: WeatherEntity? = null,
    val error: String? = null,
    val inputError: String? = null
)