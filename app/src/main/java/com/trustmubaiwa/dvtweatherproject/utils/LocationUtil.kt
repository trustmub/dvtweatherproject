package com.trustmubaiwa.dvtweatherproject.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.android.gms.location.*
import com.trustmubaiwa.dvtweatherproject.models.LocationModel
import com.trustmubaiwa.dvtweatherproject.common.setPrefValue
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants.Preference.CURRENT_LAT_LONG_KEY
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LocationUtils @Inject constructor(private val dataStore: DataStore<Preferences>, private val context: Context) {

    private val currentLocation = LocationModel(latitude = 0F, longitude = 0F)
    private var locationRequest: LocationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }
    private var locationCallback: LocationCallback
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    init {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null) {
                    val latLong = "${locationResult.locations[0].latitude.toFloat()},${locationResult.locations[0].longitude.toFloat()}"
                    runBlocking { dataStore.setPrefValue(CURRENT_LAT_LONG_KEY, latLong) }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun requestLocation() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun getCurrentLocation(): LocationModel = currentLocation
}