package com.zalesskyi.android.weatherapp.data.models

import com.google.android.gms.maps.model.LatLng
import com.zalesskyi.android.weatherapp.data.base.Model
import kotlinx.android.parcel.Parcelize

interface Place : Model<String> {
    val name: String?
    val latLng: LatLng?
    var temperature: Float?
}

@Parcelize
data class PlaceModel(override var id: String?,
                      override val name: String?,
                      override val latLng: LatLng?,
                      override var temperature: Float? = null) : Place