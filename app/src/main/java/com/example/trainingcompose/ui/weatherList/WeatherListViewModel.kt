package com.example.trainingcompose.ui.weatherList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingcompose.data.model.state.WeatherUiState
import com.example.trainingcompose.domain.error.LatValidationError
import com.example.trainingcompose.domain.error.LongValidationError
import com.example.trainingcompose.domain.error.NullFieldError
import com.example.trainingcompose.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {
    private val _state = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val state: StateFlow<WeatherUiState> = _state.asStateFlow()

    fun handleIntent(intent: WeatherIntent) {
        when (intent) {
            is WeatherIntent.FetchWeather -> fetchWeather(intent.latitude, intent.longitude)
        }
    }

    private fun fetchWeather(latitude: String, longitude: String) {
        viewModelScope.launch {
            // Validate inputs
            val lat = latitude.toDoubleOrNull()
            val lon = longitude.toDoubleOrNull()

            _state.value = WeatherUiState.Loading
            try {
                getWeatherUseCase.invoke(GetWeatherUseCase.Params(lat, lon)).fold(
                    onSuccess = { data ->
                        _state.value = WeatherUiState.Success(data)
                    },
                    onFailure = { failure ->
                        when (failure) {
                            NullFieldError -> _state.value = WeatherUiState.Error("Please enter valid numbers for latitude and longitude")
                            LatValidationError -> _state.value = WeatherUiState.Error("Latitude must be between -90 and 90")
                            LongValidationError -> _state.value = WeatherUiState.Error("Longitude must be between -180 and 180")
                        }
                    }
                )
            } catch (e: Exception) {
                _state.value = WeatherUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}