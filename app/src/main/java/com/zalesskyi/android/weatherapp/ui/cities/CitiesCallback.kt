package com.zalesskyi.android.weatherapp.ui.cities

import com.zalesskyi.android.weatherapp.data.models.Place

interface CitiesCallback {

    fun openDetail(place: Place)
}