package com.trustmubaiwa.dvtweatherproject.repository

import com.trustmubaiwa.dvtweatherproject.services.models.CurrentWeatherResponse
import com.trustmubaiwa.dvtweatherproject.services.models.ForecastWeatherResponse

interface IWeatherRepository {

    suspend fun getCurrentWeather(latitude: Float, longitude: Float): CurrentWeatherResponse?

    suspend fun getWeatherForecast(latitude: Float, longitude: Float): ForecastWeatherResponse?
}