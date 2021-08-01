package com.trustmubaiwa.dvtweatherproject.services.models


import com.google.gson.annotations.SerializedName

data class ForecastWeatherResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int = 0,
    @SerializedName("cod")
    val cod: String = "",
    @SerializedName("message")
    val message: Int = 0,
    @SerializedName("list")
    val list: List<ListItem>?
)

data class ForecastCoord(
    @SerializedName("lon")
    val lon: Double = 0.0,
    @SerializedName("lat")
    val lat: Double = 0.0
)


data class ForecastWind(
    @SerializedName("deg")
    val deg: Int = 0,
    @SerializedName("speed")
    val speed: Double = 0.0,
    @SerializedName("gust")
    val gust: Double = 0.0
)


data class ForecastWeatherItem(
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("main")
    val main: String = "",
    @SerializedName("id")
    val id: Int = 0
)


data class ForecastClouds(
    @SerializedName("all")
    val all: Int = 0
)


data class City(
    @SerializedName("country")
    val country: String = "",
    @SerializedName("coord")
    val coord: ForecastCoord,
    @SerializedName("sunrise")
    val sunrise: Int = 0,
    @SerializedName("timezone")
    val timezone: Int = 0,
    @SerializedName("sunset")
    val sunset: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("population")
    val population: Int = 0
)


data class ListItem(
    @SerializedName("dt")
    val dt: Int = 0,
    @SerializedName("pop")
    val pop: Int = 0,
    @SerializedName("visibility")
    val visibility: Int = 0,
    @SerializedName("dt_txt")
    val dtTxt: String = "",
    @SerializedName("weather")
    val weather: List<ForecastWeatherItem>?,
    @SerializedName("main")
    val main: ForecastMain,
    @SerializedName("clouds")
    val clouds: ForecastClouds,
    @SerializedName("sys")
    val sys: ForecastSys,
    @SerializedName("wind")
    val wind: ForecastWind
)


data class ForecastSys(
    @SerializedName("pod")
    val pod: String = ""
)


data class ForecastMain(
    @SerializedName("temp")
    val temp: Double = 0.0,
    @SerializedName("temp_min")
    val tempMin: Double = 0.0,
    @SerializedName("grnd_level")
    val grndLevel: Int = 0,
    @SerializedName("temp_kf")
    val tempKf: Double = 0.0,
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("pressure")
    val pressure: Int = 0,
    @SerializedName("sea_level")
    val seaLevel: Int = 0,
    @SerializedName("feels_like")
    val feelsLike: Double = 0.0,
    @SerializedName("temp_max")
    val tempMax: Double = 0.0
)
