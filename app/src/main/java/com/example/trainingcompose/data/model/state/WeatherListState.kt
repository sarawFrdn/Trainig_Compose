package com.example.trainingcompose.data.model.state

import com.example.trainingcompose.domain.entity.WeatherEntity

sealed interface WeatherUiState {
    data object Idle : WeatherUiState
    data object Loading : WeatherUiState
    data class Error(val message: String) : WeatherUiState
    data class Success(val data: WeatherEntity) : WeatherUiState
}