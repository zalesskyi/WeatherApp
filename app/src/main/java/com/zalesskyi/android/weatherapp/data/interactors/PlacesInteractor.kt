package com.zalesskyi.android.weatherapp.data.interactors

import com.google.android.libraries.places.api.model.Place
import io.reactivex.Single

interface PlacesInteractor {

    fun loadPlaces(): Single<List<Place>>

    fun addPlace(placeId: String): Single<Place>
}

class PlacesInteractorImpl : PlacesInteractor {

    override fun loadPlaces(): Single<List<Place>> {
        TODO()
    }

    override fun addPlace(placeId: String): Single<Place> {
        TODO()
    }
}