package com.trustmubaiwa.dvtweatherproject.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.trustmubaiwa.dvtweatherproject.models.DayWeatherModel
import com.trustmubaiwa.dvtweatherproject.models.ForecastWeatherModel
import com.trustmubaiwa.dvtweatherproject.services.models.CurrentWeatherResponse
import com.trustmubaiwa.dvtweatherproject.services.models.ForecastWeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


fun <T> DataStore<Preferences>.getValueFlow(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
    return this.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[key] ?: defaultValue
        }
}

suspend fun <T> DataStore<Preferences>.setPrefValue(key: Preferences.Key<T>, value: T) {
    this.edit { preferences ->
        preferences[key] = value
    }
}

fun Double.kelvinToCelsius(): Double {
    return (this - 273.15)
}

fun Int.toWeekDay(): String {
    return try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this@toWeekDay * 1000L
        SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar.time)
    } catch (e: Exception) {
        ""
    }
}

fun CurrentWeatherResponse.mapDailyWeatherData(): DayWeatherModel? {
    return DayWeatherModel(
        latitude = this.coord.lat,
        longitude = this.coord.lon,
        icon = this.weather?.first()?.icon ?: "",
        main = this.weather?.first()?.main ?: "",
        description = this.weather?.first()?.description ?: "",
        country = this.sys.country,
        temp = "${this.main.temp.kelvinToCelsius().toInt()}°",
        tempMin = "${this.main.tempMin.kelvinToCelsius().toInt()}°",
        tempMax = "${this.main.tempMax.kelvinToCelsius().toInt()}°",
        humidity = this.main.humidity,
        windSpeed = this.wind.speed,
    )
}

fun ForecastWeatherResponse.mapForecastWeatherData(): List<ForecastWeatherModel> {
    val listItems = mutableListOf<ForecastWeatherModel>()
    this.list?.forEach {
        listItems.add(
            ForecastWeatherModel(
                latitude = this.city.coord.lat,
                longitude = this.city.coord.lon,
                name = this.city.name,
                main = it.weather?.first()?.main ?: "",
                icon = it.weather?.first()?.icon ?: "",
                temp = "${it.main.temp.kelvinToCelsius().toInt()}°",
                dt = it.dt,
                dateTxt = it.dtTxt
            )
        )
    }

    return listItems
}