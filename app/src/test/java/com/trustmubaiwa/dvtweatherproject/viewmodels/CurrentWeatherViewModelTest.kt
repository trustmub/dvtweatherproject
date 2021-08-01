package com.trustmubaiwa.dvtweatherproject.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants
import com.trustmubaiwa.dvtweatherproject.common.getValueFlow
import com.trustmubaiwa.dvtweatherproject.models.LocationModel
import com.trustmubaiwa.dvtweatherproject.repository.WeatherRepository
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CurrentWeatherViewModelTest : TestCase() {

    private lateinit var viewModel: CurrentWeatherViewModel

    @Mock
    lateinit var mockRepository: WeatherRepository

    @Mock
    lateinit var mockDataStore: DataStore<Preferences>

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.openMocks(this)

        viewModel = CurrentWeatherViewModel(mockRepository, mockDataStore, Dispatchers.Unconfined)
    }

    @Test
    fun `test getLocationValues should set the locationValues value from local storage`() = runBlocking {
        val flow: Flow<String> = kotlinx.coroutines.flow.flow { emit("12.3,12.3") }
        val expectedResult = LocationModel(12.3f, 12.3f)

        whenever(mockDataStore.getValueFlow<String>(WeatherConstants.Preference.CURRENT_LAT_LONG_KEY, any())).thenReturn(flow)

        viewModel.fetchLocationValues()

        assertEquals(expectedResult, viewModel.getCurrentLocationLatLong().value!!)
    }

    @Test
    fun `test formatValueForLocation should format the to LocationModel`() {
        val coordinates = "12.3,12.3"

        viewModel.formatValueForLocation(coordinates)

        assertEquals(12.3f, viewModel.getCurrentLocationLatLong().value!!.latitude)
    }

}