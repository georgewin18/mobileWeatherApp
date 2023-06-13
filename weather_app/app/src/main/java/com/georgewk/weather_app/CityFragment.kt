package com.georgewk.weather_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.georgewk.weather_app.common.Common
import com.georgewk.weather_app.databinding.FragmentCityBinding
import com.georgewk.weather_app.retrofit.IOpenWeatherMap
import com.georgewk.weather_app.retrofit.RetrofitClient
import com.mancj.materialsearchbar.MaterialSearchBar
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class CityFragment : Fragment() {

    private lateinit var binding: FragmentCityBinding
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var mService: IOpenWeatherMap
    private lateinit var retrofit: Retrofit
    private lateinit var searchBar: MaterialSearchBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable = CompositeDisposable()
        retrofit = RetrofitClient.loadData()
        mService = retrofit.create(IOpenWeatherMap::class.java)

        searchBar = binding.searchBar
        searchBar.setHint("City Name...")

        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onButtonClicked(buttonCode: Int) {
                TODO("Not yet implemented")
            }

            override fun onSearchStateChanged(enabled: Boolean) {
                val test = if (enabled) "enabled" else "disabled"
                Toast.makeText(context, "Search $test", Toast.LENGTH_SHORT).show()
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                if (text != null) {
                    getWeatherInformation(text.toString())
                }
            }

        })
    }

    private fun  getWeatherInformation(city: String) {

        compositeDisposable.add(
            mService.getWeatherByCityName(
                city,
                Common.APP_ID, "metric"
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        Picasso.get().load("https://openweathermap.org/img/w/${result.weather?.get(0)?.icon}.png").into(binding.imgWeather)

                        binding.cityName.text = result.name
                        binding.txtGeoCoordinate.text = StringBuilder("[${result.coord?.lat},${result.coord?.lon}]")
                        binding.txtDescription.text = StringBuilder("Weather in ${result.weather?.get(0)?.description}")
                        binding.txtTemperature.text = StringBuilder("${result.main?.temp}°C")
                        binding.txtPressure.text = StringBuilder("${result.main?.pressure} hpa")
                        binding.txtDateTime.text = result.dt?.let { Common.converUnixToDate(it) }
                        binding.txtHumidity.text = StringBuilder("${result.main?.humidity}%")
                        binding.txtSunrise.text = result.sys?.sunrise?.let { Common.converUnixToDate(it)}
                        binding.txtSunset.text = result.sys?.sunset?.let { Common.converUnixToDate(it)}
                        binding.txtWind.text = StringBuilder("Speed: ${result.wind?.speed} deg: ${result.wind?.deg}")

                        binding.weatherPanel.visibility = View.VISIBLE
                        binding.loading.visibility = View.GONE
                    },
                    {
                            t ->
                        t.printStackTrace()
                        binding.loading.visibility = View.GONE
                        Toast.makeText(context, "City Not Found", Toast.LENGTH_SHORT).show()
                    }

                )
        )
    }
}