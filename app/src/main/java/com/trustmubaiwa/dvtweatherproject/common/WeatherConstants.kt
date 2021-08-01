package com.trustmubaiwa.dvtweatherproject.common

import androidx.datastore.preferences.core.preferencesKey

@Retention(AnnotationRetention.SOURCE)
annotation class WeatherConstants {
    object Api {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val KEY = "b02537992c56a74cafc823c8e65788f7"
    }

    object Preference {
        const val DATA_STORE_PREFERENCES = "preference_data_store"
        const val FAHRENHEIT_UNIT = "fahrenheit"
        const val DEGREES_UNIT = "degrees"
        val CURRENT_LAT_LONG_KEY = preferencesKey<String>("lat_long")
        val UNIT_OF_MEASURE_KEY = preferencesKey<String>("temp_measure_unit")
    }

    object WeatherType {
        const val CLEAR = "clear"
        const val SUNNY = "sunny"
        const val RAINY = "rainy"
        const val CLOUDY = "cloudy"
    }
}