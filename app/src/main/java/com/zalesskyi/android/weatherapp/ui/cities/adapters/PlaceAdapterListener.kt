package com.zalesskyi.android.weatherapp.ui.cities.adapters

import com.zalesskyi.android.weatherapp.data.models.Place

interface PlaceAdapterListener {

    fun onPlaceClick(place: Place)
}