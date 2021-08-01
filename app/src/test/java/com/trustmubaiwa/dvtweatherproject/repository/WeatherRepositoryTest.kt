package com.trustmubaiwa.dvtweatherproject.repository

import com.google.gson.Gson
import com.trustmubaiwa.dvtweatherproject.common.DataState
import com.trustmubaiwa.dvtweatherproject.services.WeatherService
import junit.framework.TestCase

import com.nhaarman.mockitokotlin2.*
import com.trustmubaiwa.dvtweatherproject.services.models.CurrentWeatherResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response


class WeatherRepositoryTest : TestCase() {

    lateinit var repository: WeatherRepository

    @Mock
    lateinit var mockService: WeatherService

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)

        repository = WeatherRepository(mockService)
    }

    @Test
    fun `test fetchCurrentWeather should emit loading state as the first result`() = runBlocking {
        val long: Float = 12.1.toFloat()
        val lat: Float = 12.1.toFloat()

        val result = repository.fetchCurrentWeather(lat, long)

        assertEquals(DataState.Loading, result.first())
    }

    @Test
    fun `test fetchCurrentWeather should return emit success with DataState Success when service call is successful`() = runBlocking {
        val long: Float = 12.1.toFloat()
        val lat: Float = 12.1.toFloat()
        val response = successfulResult()

        whenever(mockService.getCurrentWeather(any(), any(), any())).thenReturn(response)

        val result = repository.fetchCurrentWeather(lat, long)
        val r = result.take(2).toList()

//        assertEquals(2, result.count())
        assertEquals(DataState.Success(response), r[2])
//        assertEquals(DataState.Success(response), result.count)
//        assertEquals(DataState.Success(response), result.drop(1).first())

    }

    private fun successfulResult(): Response<CurrentWeatherResponse> {
        val responseString =
            """{"coord":{"lon":27.9483,"lat":26.3008},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"base":"stations","main":{"temp":301.83,"feels_like":300.57,"temp_min":301.83,"temp_max":301.83,"pressure":1009,"humidity":26,"sea_level":1009,"grnd_level":967},"visibility":10000,"wind":{"speed":5.29,"deg":5,"gust":9.63},"clouds":{"all":0},"dt":1627155207,"sys":{"country":"EG","sunrise":1627097367,"sunset":1627145979},"timezone":7200,"id":350661,"name":"Qaşr al Farāfirah","cod":200}"""
        val serialisedResult = Gson().fromJson(responseString, CurrentWeatherResponse::class.java)
        val result = Response.success(serialisedResult)
        return Response.success(serialisedResult)
    }
}
