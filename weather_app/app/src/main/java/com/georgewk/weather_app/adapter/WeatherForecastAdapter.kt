package com.georgewk.weather_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.georgewk.weather_app.common.Common
import com.georgewk.weather_app.databinding.ItemWeatherForecastBinding
import com.georgewk.weather_app.retrofit.ListItem
import com.squareup.picasso.Picasso

class WeatherForecastAdapter(private val weatherForecastAdapter: ArrayList<ListItem>, val context: Context) :  RecyclerView.Adapter<WeatherForecastAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = ItemWeatherForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val weatherForecast = weatherForecastAdapter[position]

        with(holder.binding) {
            Picasso.get().load("https://openweathermap.org/img/w/${weatherForecast.weather.get(0).icon}.png").into(imgWeather)
            txtDate.text = StringBuilder("${Common.converUnixToDate(weatherForecast.dt)}")
            txtDescription.text = StringBuilder(weatherForecast.weather.get(0).description)
            txtTemperature.text = StringBuilder("${weatherForecast.main.temp}°C")
        }
    }

    class RecyclerViewHolder(var binding: ItemWeatherForecastBinding) : RecyclerView.ViewHolder(binding.root)
    override fun getItemCount(): Int {
        return weatherForecastAdapter.size
    }
}


