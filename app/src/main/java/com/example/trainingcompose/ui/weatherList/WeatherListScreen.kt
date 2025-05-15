package com.example.trainingcompose.ui.weatherList


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trainingcompose.data.model.entity.WeatherEntity


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
            isError = state.inputError?.contains("Latitude") == true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Longitude input
        OutlinedTextField(
            value = longitude,
            onValueChange = { longitude = it },
            label = { androidx.compose.material3.Text("Longitude") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.inputError?.contains("Longitude") == true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Fetch button
        Button(
            onClick = { viewModel.handleIntent(WeatherIntent.FetchWeather(latitude, longitude)) },
            modifier = Modifier.fillMaxWidth()
        ) {
          Text("Get Weather")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Display state
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            state.inputError != null -> {
               Text(
                    text = state.inputError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            state.error != null -> {
                androidx.compose.material3.Text(
                    text = "Error: ${state.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            state.weather != null -> {
                WeatherItem(state.weather)
            }
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
            Text(
                text = "Temperature: ${weather.temperature}°C",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Humidity: ${weather.humidity}%",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Wind Speed: ${weather.windSpeed} km/h",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


