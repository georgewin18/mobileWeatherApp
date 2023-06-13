package com.georgewk.weather_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.georgewk.weather_app.adapter.WeatherForecastAdapter
import com.georgewk.weather_app.common.Common
import com.georgewk.weather_app.databinding.FragmentForecastBinding
import com.georgewk.weather_app.retrofit.IOpenWeatherMap
import com.georgewk.weather_app.retrofit.ListItem
import com.georgewk.weather_app.retrofit.RetrofitClient
import com.georgewk.weather_app.retrofit.WeatherForecastResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class ForecastFragment : Fragment() {

    private lateinit var binding: FragmentForecastBinding
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var mService :IOpenWeatherMap
    private lateinit var retrofit: Retrofit
    private lateinit var rvListItem: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable = CompositeDisposable()
        retrofit = RetrofitClient.loadData()
        mService = retrofit.create(IOpenWeatherMap::class.java)

        rvListItem = binding.recyclerForecast
        rvListItem.setHasFixedSize(true)

        getForecastWeatherInformation()
    }

    private fun getForecastWeatherInformation() {
        compositeDisposable.add(
            mService.getForecastWeatherByLatLng(
                Common.current_location?.latitude.toString(),
                Common.current_location?.longitude.toString(),
                Common.APP_ID)
                .subscribeOn(
                    Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        displayForecastWeather(it)
                    },
                    {
                        it.printStackTrace()
                    }
                )

        )
    }

    private fun displayForecastWeather(weatherForecastResult : WeatherForecastResponse) {
        binding.txtCityName.text = weatherForecastResult.city.name
        binding.txtGeoCoord.text = weatherForecastResult.city.coord.toString()

        rvListItem.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = WeatherForecastAdapter(weatherForecastResult.list as ArrayList<ListItem>, requireContext())
        rvListItem.adapter = adapter
    }
}