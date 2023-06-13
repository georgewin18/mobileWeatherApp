package com.georgewk.weather_app.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.georgewk.weather_app.CityFragment
import com.georgewk.weather_app.ForecastFragment
import com.georgewk.weather_app.TodayWeatherFragment

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = TodayWeatherFragment()
            1 -> fragment = ForecastFragment()
            2 -> fragment = CityFragment()
        }
        return fragment as Fragment
    }
}