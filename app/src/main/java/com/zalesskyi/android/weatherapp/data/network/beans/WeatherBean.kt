package com.zalesskyi.android.weatherapp.data.network.beans

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherBean(@JsonProperty("main")
                       val main: MainBean)