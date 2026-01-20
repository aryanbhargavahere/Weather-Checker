package com.example.weatherchecker.utild

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.FormBody

object Constants {
    const val APP_ID="4aca44830028b4e06c51e0d6a5426571"
    const val BASE_URl="https://api.openweathermap.org/data/"
    const val METRIC_UNIT="metric"
    fun isnetworkavailable(context: Context): Boolean {
        // get an instance of connectivity manager using the context
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        //To check the currently active network amd return falso if none
        val network = connectivityManager.activeNetwork ?: return false
        //to check the capability of network
        val activenetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        //TO check the type of netwoek
        return activenetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                activenetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                activenetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}
