package com.georgewk.weather_app.common

import android.location.Location
import java.text.SimpleDateFormat
import java.util.*

class Common {
    companion object {
        val APP_ID = "43b73dc8290fbbde40baf39532cfa832"
        var current_location: Location? = null

        fun converUnixToDate(dt: Int): String? {
            val date = Date(dt*1000L)
            val sdf = SimpleDateFormat("HH:mm")
            return sdf.format(date)
        }
        fun convertUnixToHour(dt: Int): String? {
            val date = Date(dt*1000L)
            val sdf = SimpleDateFormat("HH:mm EEE MM yyyy")
            return sdf.format(date)
        }
    }
}