package com.example.trainingcompose.ui.weatherList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingcompose.data.model.state.WeatherState
import com.example.trainingcompose.data.repository.WeatherRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    fun handleIntent(intent: WeatherIntent) {
        when (intent) {
            is WeatherIntent.FetchWeather -> fetchWeather(intent.latitude, intent.longitude)
        }
    }

    private fun fetchWeather(latitude: String, longitude: String) {
        viewModelScope.launch {
            val lat = latitude.toDoubleOrNull()
            val lon = longitude.toDoubleOrNull()
            if (lat == null || lon == null) {
                _state.value = WeatherState(inputError = "Please enter valid numbers for latitude and longitude")
                return@launch
            }
            if (lat < -90 || lat > 90) {
                _state.value = WeatherState(inputError = "Latitude must be between -90 and 90")
                return@launch
            }
            if (lon < -180 || lon > 180) {
                _state.value = WeatherState(inputError = "Longitude must be between -180 and 180")
                return@launch
            }

            _state.value = WeatherState(isLoading = true)
            try {
                val weather = repository.getWeather(lat, lon)
                _state.value = WeatherState(weather = weather)
            } catch (e: Exception) {
                _state.value = WeatherState(error = e.message)
            }
        }
    }
}