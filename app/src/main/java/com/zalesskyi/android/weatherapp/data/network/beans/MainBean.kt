package com.zalesskyi.android.weatherapp.data.network.beans

import com.fasterxml.jackson.annotation.JsonProperty

data class MainBean(@JsonProperty("temp") val temp: Float?)