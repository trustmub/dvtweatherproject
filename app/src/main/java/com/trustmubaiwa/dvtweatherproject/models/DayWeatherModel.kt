package com.trustmubaiwa.dvtweatherproject.models

data class DayWeatherModel(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var icon: String = "",
    var main: String = "",
    var description: String = "",
    var country: String = "",
    var temp: String = "0",
    var tempMin: String = "0",
    var tempMax: String = "0",
    var humidity: Int = 0,
    val windSpeed: Double = 0.0,
)