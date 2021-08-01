package com.trustmubaiwa.dvtweatherproject.viewmodels

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trustmubaiwa.dvtweatherproject.common.DataState
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants.Preference.CURRENT_LAT_LONG_KEY
import com.trustmubaiwa.dvtweatherproject.common.getValueFlow
import com.trustmubaiwa.dvtweatherproject.common.mapDailyWeatherData
import com.trustmubaiwa.dvtweatherproject.common.mapForecastWeatherData
import com.trustmubaiwa.dvtweatherproject.di.IoDispatcher
import com.trustmubaiwa.dvtweatherproject.models.DayWeatherModel
import com.trustmubaiwa.dvtweatherproject.models.ForecastWeatherModel
import com.trustmubaiwa.dvtweatherproject.models.LocationModel
import com.trustmubaiwa.dvtweatherproject.repository.WeatherRepository
import com.trustmubaiwa.dvtweatherproject.services.models.CurrentWeatherResponse
import com.trustmubaiwa.dvtweatherproject.services.models.ForecastWeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val dataStore: DataStore<Preferences>,
    @IoDispatcher private val coroutineContext: CoroutineDispatcher
) : ViewModel() {

    private val _locationValues: MutableLiveData<LocationModel> = MutableLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    private val isForecastLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    private val isSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isForecastSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _fetchingErrorState: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _dayWeatherData: MutableLiveData<DayWeatherModel> = MutableLiveData()
    private val _forecastWeatherData: MutableLiveData<List<ForecastWeatherModel>> = MutableLiveData()

    fun getCurrentLocationLatLong(): LiveData<LocationModel> = _locationValues
    fun getFetchingErrorState(): LiveData<Boolean> = _fetchingErrorState
    fun isLoading(): LiveData<Boolean> = isLoading
    fun isSuccess(): LiveData<Boolean> = isSuccess
    fun getDayWeatherData(): LiveData<DayWeatherModel> = _dayWeatherData

    fun isForecastLoading(): LiveData<Boolean> = isForecastLoading
    fun isForecastSuccess(): LiveData<Boolean> = isForecastSuccess
    fun getForecastWeatherData(): LiveData<List<ForecastWeatherModel>> = _forecastWeatherData

    fun fetchLocationValues() {
        viewModelScope.launch {
            withContext(coroutineContext) {
                val ret = dataStore.getValueFlow<String>(CURRENT_LAT_LONG_KEY, "-0.0,0.0")
                ret.collect {
                    formatValueForLocation(it)
                }
            }
        }
    }

    fun fetchWeatherLocation(latitude: Float, longitude: Float) {
        viewModelScope.launch {
            withContext(coroutineContext) {
                repository.fetchCurrentWeather(lat = latitude, lon = longitude).catch {
                    Log.d("NetworkError", "Failed fetching weather information")
                }.collect {
                    processNetworkWeatherData(it)
                }
            }
        }
    }

    fun fetchWeatherForecast(latitude: Float, longitude: Float) {
        isForecastLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineContext) {
                    repository.fetchWeatherForecast(latitude, longitude)
                }
            }.onSuccess {
                processForecastWeatherData(it)
            }.onFailure {
                isForecastLoading.postValue(false)
                isForecastSuccess.postValue(false)
            }
        }
    }

    private fun processForecastWeatherData(it: ForecastWeatherResponse?) {
        if (it != null) {
            val filtered = it.mapForecastWeatherData().filter {
                val lstIndex = it.dateTxt.lastIndex
                val sttIndex = lstIndex - 7
                val sub = it.dateTxt.subSequence(sttIndex, lstIndex)
                sub == "12:00:00" || sub == "12:00:0"
            }
            _forecastWeatherData.postValue(filtered)
            isForecastSuccess.postValue(true)
        } else {
            isForecastSuccess.postValue(false)
        }
        isForecastLoading.postValue(false)
    }

    @VisibleForTesting
    fun formatValueForLocation(locationString: String) {
        val location = locationString.split(",")
        _locationValues.value = LocationModel(latitude = location[0].toFloat(), longitude = location[1].toFloat())
    }

    private fun processNetworkWeatherData(dataState: DataState<CurrentWeatherResponse>) {
        when (dataState) {
            is DataState.Loading -> renderLoadingDataState()

            is DataState.Success -> renderSuccessDataState(dataState.data)

            is DataState.EmptyData -> renderEmptyDataState()

            is DataState.Error -> renderErrorDataState(dataState.exception)
        }
    }

    private fun renderLoadingDataState() {
        isLoading.postValue(true)
        isSuccess.postValue(false)
        _fetchingErrorState.postValue(false)
    }

    private fun renderSuccessDataState(data: CurrentWeatherResponse) {
        _dayWeatherData.postValue(data.mapDailyWeatherData())
        isLoading.postValue(false)
        isSuccess.postValue(true)
    }

    private fun renderEmptyDataState() {
        isLoading.postValue(false)
        isSuccess.postValue(false)
    }

    private fun renderErrorDataState(exception: Exception) {
        isLoading.postValue(false)
        isSuccess.postValue(false)
        _fetchingErrorState.postValue(true)
    }

}