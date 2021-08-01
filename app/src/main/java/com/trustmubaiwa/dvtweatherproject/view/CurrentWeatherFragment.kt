package com.trustmubaiwa.dvtweatherproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.trustmubaiwa.dvtweatherproject.R
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants.WeatherType.CLEAR
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants.WeatherType.CLOUDY
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants.WeatherType.RAINY
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants.WeatherType.SUNNY
import com.trustmubaiwa.dvtweatherproject.databinding.FragmentCurrentWeatherBinding
import com.trustmubaiwa.dvtweatherproject.viewmodels.CurrentWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {

    private lateinit var binding: FragmentCurrentWeatherBinding
    private val viewModel: CurrentWeatherViewModel by viewModels<CurrentWeatherViewModel>()

    private var weatherForecastAdapter: ForecastAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCurrentWeatherBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseObservers()

        viewModel.fetchLocationValues()

    }

    private fun initialiseObservers() {
        viewModel.getCurrentLocationLatLong().observe(viewLifecycleOwner) {
            viewModel.fetchWeatherLocation(it.latitude, it.longitude)
            viewModel.fetchWeatherForecast(it.latitude, it.longitude)
        }

        viewModel.getDayWeatherData().observe(viewLifecycleOwner) {
            when {
                it.main.lowercase() == CLEAR -> mapWeatherDataToUI(R.drawable.forest_sunny, R.color.primary_sunny)
                it.main.lowercase() == CLOUDY -> mapWeatherDataToUI(R.drawable.forest_cloudy, R.color.primary_cloudy)
                it.main.lowercase() == SUNNY -> mapWeatherDataToUI(R.drawable.forest_sunny, R.color.primary_sunny)
                it.main.lowercase() == RAINY -> mapWeatherDataToUI(R.drawable.forest_rainy, R.color.primary_rainy)
                else -> mapWeatherDataToUI(R.drawable.forest_sunny, R.color.primary_sunny)
            }
        }

        viewModel.getForecastWeatherData().observe(viewLifecycleOwner) {
            weatherForecastAdapter = ForecastAdapter(it)
            binding.weatherForecastRecyclerView.adapter = weatherForecastAdapter
            binding.weatherForecastRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun mapWeatherDataToUI(@DrawableRes backgroundImg: Int, @ColorRes backgroundColor: Int) {
        context?.let { ctx ->
            val img = ContextCompat.getDrawable(ctx, backgroundImg)
            binding.dayWeatherImage.setImageDrawable(img)
            binding.root.setBackgroundColor(ContextCompat.getColor(ctx, backgroundColor))
        }
    }
}