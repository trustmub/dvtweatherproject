package com.trustmubaiwa.dvtweatherproject.repository

import com.trustmubaiwa.dvtweatherproject.common.BaseRepository
import com.trustmubaiwa.dvtweatherproject.common.DataState
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants
import com.trustmubaiwa.dvtweatherproject.services.WeatherService
import com.trustmubaiwa.dvtweatherproject.services.models.CurrentWeatherResponse
import com.trustmubaiwa.dvtweatherproject.services.models.ForecastWeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class WeatherRepository @Inject constructor(private val service: WeatherService) : IWeatherRepository, BaseRepository() {

    override suspend fun getCurrentWeather(latitude: Float, longitude: Float): CurrentWeatherResponse? {
        return try {
            safeApiCall { service.getCurrentWeather(latitude, longitude, WeatherConstants.Api.KEY) }
        } catch (e: Exception) {
            throw  e
        }
    }

    fun fetchCurrentWeather(lat: Float, lon: Float): Flow<DataState<CurrentWeatherResponse>> = flow {
        emit(DataState.Loading)
        try {
            val result: CurrentWeatherResponse? = getCurrentWeather(lat, lon)
            if (result != null) {
                emit(DataState.Success(result))
            } else {
                emit(DataState.EmptyData)
            }

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun getWeatherForecast(latitude: Float, longitude: Float): ForecastWeatherResponse? {
        return try {
            safeApiCall { service.getWeatherForecast(latitude, longitude, WeatherConstants.Api.KEY) }
        } catch (e: Exception) {
            throw  e
        }
    }

    suspend fun fetchWeatherForecast(latitude: Float, longitude: Float): ForecastWeatherResponse? {
        try {
            val result = getWeatherForecast(latitude, longitude)
            return result
        } catch (e: Exception) {
            throw e
        }
    }

}
