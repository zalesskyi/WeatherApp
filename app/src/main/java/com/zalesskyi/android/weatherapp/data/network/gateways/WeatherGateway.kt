package com.zalesskyi.android.weatherapp.data.network.gateways

import com.zalesskyi.android.weatherapp.data.network.api.WeatherApi
import io.reactivex.Single

interface WeatherGateway {

    fun getTemperature(cityName: String): Single<Float>
}

class WeatherGatewayImpl : WeatherGateway {

    val api: WeatherApi = TODO()

    override fun getTemperature(cityName: String): Single<Float> =
        api.getWeather(cityName)
            .map { it.main.temp }
}