package com.zalesskyi.android.weatherapp.data.converters.db_converters

import com.google.android.gms.maps.model.LatLng
import com.zalesskyi.android.weatherapp.data.converters.base.BaseConverter
import com.zalesskyi.android.weatherapp.data.converters.base.Converter
import com.zalesskyi.android.weatherapp.data.database.models.PlaceDb
import com.zalesskyi.android.weatherapp.data.models.Place
import com.zalesskyi.android.weatherapp.data.models.PlaceModel

interface PlaceDbConverter : Converter<Place, PlaceDb>

class PlaceDbConverterImpl : BaseConverter<Place, PlaceDb>(), PlaceDbConverter {

    override fun processConvertInToOut(inObject: Place): PlaceDb? = inObject.run {
        PlaceDb(id, name, latLng?.latitude, latLng?.longitude, temperature)
    }

    override fun processConvertOutToIn(outObject: PlaceDb): Place? = outObject.run {
        PlaceModel(placeId, name, LatLng(lat ?: .0, lng ?: .0), temperature)
    }
}