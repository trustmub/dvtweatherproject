package com.trustmubaiwa.dvtweatherproject.models

data class ForecastWeatherModel(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var main: String = "",
    var name: String = "",
    var icon: String = "",
    var temp: String = "0",
    var dt: Int = 0,
    var dateTxt: String= "0"
)