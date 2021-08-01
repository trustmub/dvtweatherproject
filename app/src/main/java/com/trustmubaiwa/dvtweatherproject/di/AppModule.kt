package com.trustmubaiwa.dvtweatherproject.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants.Preference.DATA_STORE_PREFERENCES
import com.trustmubaiwa.dvtweatherproject.services.WeatherService
import com.trustmubaiwa.dvtweatherproject.utils.LocationUtils
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = WeatherConstants.Api.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (true) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(WeatherService::class.java)

    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.createDataStore(name = DATA_STORE_PREFERENCES)
    }

    @Provides
    @Singleton
    fun providePreferenceUtil(@ApplicationContext context: Context, dataStore: DataStore<Preferences>) : LocationUtils {
        return LocationUtils(dataStore, context)
    }
}