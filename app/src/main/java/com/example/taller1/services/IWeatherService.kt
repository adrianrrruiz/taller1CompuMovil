package com.example.taller1.services

import com.example.taller1.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherService {
    @GET("current.json")
    fun getCurrentWeather(@Query("q") city: String, @Query("key") apiKey: String): Call<WeatherResponse>
}