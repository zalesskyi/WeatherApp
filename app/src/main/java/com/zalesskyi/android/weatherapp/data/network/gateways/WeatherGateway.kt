package com.zalesskyi.android.weatherapp.data.network.gateways

import com.zalesskyi.android.weatherapp.data.network.api.WeatherApi
import io.reactivex.Single
import javax.inject.Inject

interface WeatherGateway {

    fun getTemperature(cityName: String): Single<Float>
}

class WeatherGatewayImpl @Inject constructor(val api: WeatherApi) : WeatherGateway {

    override fun getTemperature(cityName: String): Single<Float> =
        api.getWeather(cityName)
            .map { it.main.temp }
}