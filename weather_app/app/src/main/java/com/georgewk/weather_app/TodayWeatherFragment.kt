package com.georgewk.weather_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.georgewk.weather_app.common.Common
import com.georgewk.weather_app.databinding.FragmentTodayWeatherBinding
import com.georgewk.weather_app.retrofit.IOpenWeatherMap
import com.georgewk.weather_app.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


class TodayWeatherFragment : Fragment() {

    private lateinit var binding: FragmentTodayWeatherBinding
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var mService: IOpenWeatherMap
    private lateinit var retrofit: Retrofit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodayWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable = CompositeDisposable()
        retrofit = RetrofitClient.loadData()
        mService = retrofit.create(IOpenWeatherMap::class.java)

        getWeatherInformation()
    }

    private fun getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatLng(Common.current_location?.latitude.toString(), Common.current_location?.longitude.toString(), Common.APP_ID, "metric")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    binding.cityName.text = result.name
                    binding.txtGeoCoordinate.text = result.coord.toString()
                    binding.txtDescription.text = result.weather?.get(0)?.description ?: "N/A"
                    binding.txtTemperature.text = result.main?.temp.toString()
                    binding.txtDateTime.text = result.dt?.let { Common.converUnixToDate(it) }
                    binding.txtHumidity.text = result.main?.humidity.toString()
                    binding.txtDateTime.text = result.dt?.let { Common.convertUnixToHour(it) }
                    binding.txtSunrise.text = result.sys?.sunrise?.let { Common.converUnixToDate(it) }
                    binding.txtSunset.text = result.sys?.sunset?.let { Common.converUnixToDate(it) }

                    binding.weatherPanel.visibility = View.VISIBLE
                    binding.loading.visibility = View.GONE
                },
                { t ->
                    t.printStackTrace()
                }
            )
        )
    }

}