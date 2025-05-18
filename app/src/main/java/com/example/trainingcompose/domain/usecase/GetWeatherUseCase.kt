package com.example.trainingcompose.domain.usecase

import com.example.trainingcompose.data.repository.WeatherRepository
import com.example.trainingcompose.domain.entity.WeatherEntity
import com.example.trainingcompose.domain.error.LatValidationError
import com.example.trainingcompose.domain.error.LongValidationError
import com.example.trainingcompose.domain.error.NullFieldError
import kotlin.Result.Companion.failure


interface GetWeatherUseCase {
    suspend operator fun invoke(params: Params): Result<WeatherEntity>
    data class Params(val latitude: Double?, val longitude: Double?)
}

class GetWeatherUseCaseImpl(private val repository: WeatherRepository) : GetWeatherUseCase {
    override suspend fun invoke(params: GetWeatherUseCase.Params): Result<WeatherEntity> =
        when {
            params.latitude == null || params.longitude == null -> failure(NullFieldError)
            params.latitude < -90 || params.latitude > 90 -> failure(LatValidationError)
            params.longitude < -180 || params.longitude > 180 -> failure(LongValidationError)
            else -> repository.getWeather(latitude = params.latitude, longitude = params.longitude)
        }
}

