package com.trustmubaiwa.dvtweatherproject.services

import com.trustmubaiwa.dvtweatherproject.services.models.CurrentWeatherResponse
import com.trustmubaiwa.dvtweatherproject.services.models.ForecastWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("appid") key: String
    ): Response<CurrentWeatherResponse>

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("appid") key: String,
    ): Response<ForecastWeatherResponse>
}