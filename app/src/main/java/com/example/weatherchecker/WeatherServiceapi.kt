package com.example.weatherchecker

import com.example.weatherchecker.model.WeatherResponse
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServiceapi {
    @GET("2.5/weather")
    fun getweatherdetails(
    @Query("lat") latitude:Double,
    @Query("lon") longitude: Double,
    @Query("appid") appid: String,
    @Query("units") metric: String

    ): Call<WeatherResponse>
}