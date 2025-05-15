package com.example.trainingcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.trainingcompose.data.remote.RetrofitClient
import com.example.trainingcompose.data.repository.WeatherRepositoryImpl
import com.example.trainingcompose.ui.weatherList.WeatherScreen
import com.example.trainingcompose.ui.weatherList.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherApi = RetrofitClient.weatherApi
        val repository = WeatherRepositoryImpl(weatherApi)
        val viewModel = WeatherViewModel(repository)
        setContent {
            MaterialTheme {
                Surface {
                    WeatherScreen(viewModel)
                }
            }
        }
    }
}
