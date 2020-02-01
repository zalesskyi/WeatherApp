package com.zalesskyi.android.weatherapp.data.network.api

import com.zalesskyi.android.weatherapp.BuildConfig
import com.zalesskyi.android.weatherapp.data.network.NetworkContract.V2
import com.zalesskyi.android.weatherapp.data.network.beans.WeatherBean
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("$V2/weather")
    fun getWeather(@Query("q") query: String,
                   @Query("appId") appId: String = BuildConfig.WEATHER_API_KEY): Single<WeatherBean>
}