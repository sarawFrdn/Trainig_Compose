package com.example.trainingcompose.ui.weatherList


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trainingcompose.domain.entity.WeatherEntity
import com.example.trainingcompose.data.model.state.WeatherUiState

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val state = viewModel.state.collectAsState().value
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Latitude input
        OutlinedTextField(
            value = latitude,
            onValueChange = { latitude = it },
            label = { androidx.compose.material3.Text("Latitude") },
            modifier = Modifier.fillMaxWidth(),
            isError = state is WeatherUiState.Error && state.message.contains("Latitude")
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Longitude input
        OutlinedTextField(
            value = longitude,
            onValueChange = { longitude = it },
            label = { androidx.compose.material3.Text("Longitude") },
            modifier = Modifier.fillMaxWidth(),
            isError = state is WeatherUiState.Error && state.message.contains("Longitude")
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Fetch button
        Button(
            onClick = { viewModel.handleIntent(WeatherIntent.FetchWeather(latitude, longitude)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material3.Text("Get Weather")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Display state
        when (state) {
            is WeatherUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is WeatherUiState.Error -> {
               Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is WeatherUiState.Success -> {
                WeatherItem(state.data)
            }

            else -> {}
        }
    }
}

@Composable
fun WeatherItem(weather: WeatherEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            androidx.compose.material3.Text(
                text = "Temperature: ${weather.temperature}°C",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            androidx.compose.material3.Text(
                text = "Humidity: ${weather.humidity}%",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            androidx.compose.material3.Text(
                text = "Wind Speed: ${weather.windSpeed} km/h",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}