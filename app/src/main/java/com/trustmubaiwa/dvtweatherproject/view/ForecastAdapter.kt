package com.trustmubaiwa.dvtweatherproject.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.trustmubaiwa.dvtweatherproject.R
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants.WeatherType.CLEAR
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants.WeatherType.CLOUDY
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants.WeatherType.RAINY
import com.trustmubaiwa.dvtweatherproject.common.WeatherConstants.WeatherType.SUNNY
import com.trustmubaiwa.dvtweatherproject.common.toWeekDay
import com.trustmubaiwa.dvtweatherproject.models.ForecastWeatherModel

class ForecastAdapter(private val forecastWeather: List<ForecastWeatherModel>) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_weather_item, parent, false)
        return ForecastViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = forecastWeather[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return forecastWeather.size
    }

    class ForecastViewHolder(private val view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        private val dayItemText: TextView = view.findViewById(R.id.forecastItemDay)
        private val dayItemImage: ImageView = view.findViewById(R.id.forecastItemImage)
        private val dayTemptText: TextView = view.findViewById(R.id.forecastItemTemp)

        fun bind(item: ForecastWeatherModel) {
            dayItemText.text = item.dt.toWeekDay()
            dayTemptText.text = item.temp

            when {
                item.main.lowercase() == CLOUDY -> {
                    val img = ContextCompat.getDrawable(context, R.mipmap.partlysunny)
                    dayItemImage.setImageDrawable(img)
                }
                item.main.lowercase() == SUNNY -> {
                    val img = ContextCompat.getDrawable(context, R.mipmap.clear)
                    dayItemImage.setImageDrawable(img)
                }
                item.main.lowercase() == RAINY -> {
                    val img = ContextCompat.getDrawable(context, R.mipmap.rain)
                    dayItemImage.setImageDrawable(img)
                }
                item.main.lowercase() == CLEAR -> {
                    val img = ContextCompat.getDrawable(context, R.mipmap.clear)
                    dayItemImage.setImageDrawable(img)
                }
            }
        }
    }
}