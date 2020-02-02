package com.zalesskyi.android.weatherapp.ui.cities

import android.Manifest
import android.app.Application
import androidx.annotation.RequiresPermission
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.zalesskyi.android.weatherapp.CURRENT_PLACE_ID
import com.zalesskyi.android.weatherapp.WeatherApp
import com.zalesskyi.android.weatherapp.data.interactors.PlacesInteractor
import com.zalesskyi.android.weatherapp.data.models.Place
import com.zalesskyi.android.weatherapp.data.models.PlaceModel
import com.zalesskyi.android.weatherapp.extensions.fetchPlacesSingle
import com.zalesskyi.android.weatherapp.extensions.findPredictionsSingle
import com.zalesskyi.android.weatherapp.ui.base.BaseLifecycleVM
import com.zalesskyi.android.weatherapp.utils.LocationUtils
import io.reactivex.Single
import javax.inject.Inject
import com.google.android.libraries.places.api.model.Place as GooglePlace

class CitiesVM(application: Application) : BaseLifecycleVM(application) {

    companion object {

        val PLACE_FIELDS_DETAIL = listOf(GooglePlace.Field.ID, GooglePlace.Field.NAME, GooglePlace.Field.LAT_LNG)
    }

    init {
        (application as WeatherApp).component.inject(this)
    }

    val predictionsLD = MutableLiveData<List<AutocompletePrediction>>()

    val newPlaceLD = MutableLiveData<Place>()

    val placesLD = MutableLiveData<List<Place>>()

    private val placesClient = Places.createClient(application)

    @Inject
    lateinit var placesInteractor: PlacesInteractor

    fun searchPlaces(query: String) {
        placesClient.findPredictionsSingle(createPredictionsRequest(query))
            .doAsync(predictionsLD)
    }

    /**
     * First, attempt to load place from local db.
     * If no results, then check via [LocationUtils].
     */
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun loadCurrentPlace() {
        placesInteractor.loadPlaceById(CURRENT_PLACE_ID)
            .onErrorResumeNext {
                Single.just(LocationUtils.getCurrentPlace())
                    .flatMap {
                        placesInteractor.addPlace(it)
                    }
            }
            .doAsync(newPlaceLD)
    }

    fun loadPlaces() {
        placesInteractor.loadPlaces()
            .doAsync(placesLD)
    }

    fun loadPlace(placeId: String) {
        val request = FetchPlaceRequest.builder(placeId, PLACE_FIELDS_DETAIL).build()
        placesClient.fetchPlacesSingle(request)
            .map { convertToPlace(it) }
            .flatMap { placesInteractor.addPlace(it) }
            .doAsync(newPlaceLD)
    }

    private fun createPredictionsRequest(query: String) =
        FindAutocompletePredictionsRequest.builder()
            .setSessionToken(AutocompleteSessionToken.newInstance())
            .setQuery(query)
            .setTypeFilter(TypeFilter.CITIES)
            .build()

    private fun convertToPlace(place: GooglePlace): Place = place.run {
        PlaceModel(id, name, latLng)
    }
}