package com.trustmubaiwa.dvtweatherproject.common

import com.google.gson.Gson
import com.trustmubaiwa.dvtweatherproject.services.models.CurrentWeatherResponse
import junit.framework.TestCase
import org.junit.Test

class ExtensionFunctionKtTest : TestCase() {


    @Test
    fun `test mapForecastWeatherData should return of forcust weather model list`() {

    }

    @Test
    fun `test mapDailyWeatherData should return a day weather model object`() {
        val response = getCurrentWeatherResponse()
        val result = response.mapDailyWeatherData()

        assertEquals(27.9483, result?.longitude)
        assertEquals(26.3008, result?.latitude)
        assertEquals("01n", result?.icon)
        assertEquals("Clear", result?.main)
        assertEquals("clear sky", result?.description)
        assertEquals("EG", result?.country)
        assertEquals("28°", result?.temp)
        assertEquals("28°", result?.tempMin)
        assertEquals("28°", result?.tempMax)
        assertEquals(26, result?.humidity)
        assertEquals(5.29, result?.windSpeed)
    }

    @Test
    fun `test toWeekDay should return a weeks day from a date time int`() {
        val result = 1627155207.toWeekDay()
        assertEquals("Saturday", result)
    }

    @Test
    fun `test kelvinToCelsius should convert kelvins to celsius`() {
        val result = 301.83.kelvinToCelsius()
        assertEquals(28, result)
    }

    private fun getCurrentWeatherResponse(): CurrentWeatherResponse {
        val responseString =
            """{"coord":{"lon":27.9483,"lat":26.3008},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"base":"stations","main":{"temp":301.83,"feels_like":300.57,"temp_min":301.83,"temp_max":301.83,"pressure":1009,"humidity":26,"sea_level":1009,"grnd_level":967},"visibility":10000,"wind":{"speed":5.29,"deg":5,"gust":9.63},"clouds":{"all":0},"dt":1627155207,"sys":{"country":"EG","sunrise":1627097367,"sunset":1627145979},"timezone":7200,"id":350661,"name":"Qaşr al Farāfirah","cod":200}"""
        return Gson().fromJson(responseString, CurrentWeatherResponse::class.java)
    }
}