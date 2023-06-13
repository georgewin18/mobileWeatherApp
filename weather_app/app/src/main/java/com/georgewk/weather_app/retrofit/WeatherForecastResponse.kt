package com.georgewk.weather_app.retrofit

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(

	@field:SerializedName("city")
	val city: City,

	@field:SerializedName("cnt")
	val cnt: Int,

	@field:SerializedName("cod")
	val cod: String,

	@field:SerializedName("message")
	val message: Int,

	@field:SerializedName("list")
	val list: List<ListItem>
)

data class SysForecast(

	@field:SerializedName("pod")
	val pod: String
)

data class WindForecast(

	@field:SerializedName("deg")
	val deg: Int,

	@field:SerializedName("speed")
	val speed: Any,

	@field:SerializedName("gust")
	val gust: Any
)

data class Rain(

	@field:SerializedName("3h")
	val jsonMember3h: Any
)

data class ListItem(

	@field:SerializedName("dt")
	val dt: Int,

	@field:SerializedName("pop")
	val pop: Float,

	@field:SerializedName("visibility")
	val visibility: Int,

	@field:SerializedName("dt_txt")
	val dtTxt: String,

	@field:SerializedName("weather")
	val weather: List<WeatherItemForecast>,

	@field:SerializedName("main")
	val main: MainForecast,

	@field:SerializedName("clouds")
	val clouds: CloudsForecast,

	@field:SerializedName("sys")
	val sys: SysForecast,

	@field:SerializedName("wind")
	val wind: WindForecast,

	@field:SerializedName("rain")
	val rain: Rain
)

data class City(

	@field:SerializedName("country")
	val country: String,

	@field:SerializedName("coord")
	val coord: CoordForecast,

	@field:SerializedName("sunrise")
	val sunrise: Int,

	@field:SerializedName("timezone")
	val timezone: Int,

	@field:SerializedName("sunset")
	val sunset: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("population")
	val population: Int
)

data class CoordForecast(

	@field:SerializedName("lon")
	val lon: Float,

	@field:SerializedName("lat")
	val lat: Float
)

data class CloudsForecast(

	@field:SerializedName("all")
	val all: Int
)

data class WeatherItemForecast(

	@field:SerializedName("icon")
	val icon: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("main")
	val main: String,

	@field:SerializedName("id")
	val id: Int
)

data class MainForecast(

	@field:SerializedName("temp")
	val temp: Any,

	@field:SerializedName("temp_min")
	val tempMin: Any,

	@field:SerializedName("grnd_level")
	val grndLevel: Int,

	@field:SerializedName("temp_kf")
	val tempKf: Any,

	@field:SerializedName("humidity")
	val humidity: Int,

	@field:SerializedName("pressure")
	val pressure: Int,

	@field:SerializedName("sea_level")
	val seaLevel: Int,

	@field:SerializedName("feels_like")
	val feelsLike: Any,

	@field:SerializedName("temp_max")
	val tempMax: Any
)
