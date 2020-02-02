package com.zalesskyi.android.weatherapp.data.interactors

import android.util.Log
import com.zalesskyi.android.weatherapp.TAG
import com.zalesskyi.android.weatherapp.data.database.repositories.PlacesRepository
import com.zalesskyi.android.weatherapp.data.models.Place
import com.zalesskyi.android.weatherapp.data.network.gateways.WeatherGateway
import com.zalesskyi.android.weatherapp.utils.transformers.BaseSingleTransformer
import io.reactivex.Single
import javax.inject.Inject

interface PlacesInteractor {

    /**
     * Load places from local db.
     * Also attempt to load actual temperature.
     * If we can't, then old temperature info is displayed.
     */
    fun loadPlaces(): Single<List<Place>>

    fun loadPlaceById(placeId: String): Single<Place>

    fun addPlace(place: Place): Single<Place>
}

class PlacesInteractorImpl @Inject constructor(private val weatherGateway : WeatherGateway,
                                               private val repository: PlacesRepository) : PlacesInteractor {

    private val loadTemperatureTransformer = BaseSingleTransformer<Place> {
        loadTemperature(it)
    }

    private val loadTemperaturesTransformer = BaseSingleTransformer<List<Place>> {
        loadTemperatures(it)
    }

    override fun loadPlaces(): Single<List<Place>> =
        repository.getAllPlaces()
            .compose(loadTemperaturesTransformer)
            .flatMap {
                repository.savePlaces(it)
            }

    override fun loadPlaceById(placeId: String): Single<Place> =
        repository.getById(placeId)

    override fun addPlace(place: Place): Single<Place> =
        Single.just(place)
            .compose(loadTemperatureTransformer)
            .flatMap {
                repository.savePlace(it)
            }

    private fun loadTemperature(place: Place): Single<Place> =
        Single.just(place)
            .flatMap {
                it.name?.let { placeName ->
                    weatherGateway.getTemperature(placeName)
                } ?: Single.error(IllegalStateException("Place must have a name!"))
            }
            .map { temperature ->
                place.apply {
                    this.temperature = temperature
                }
            }
            .onErrorResumeNext {
                it.message?.let { Log.e(TAG, it) }
                Single.just(place)
            }

    private fun loadTemperatures(places: List<Place>): Single<List<Place>> =
        Single.just(places)
            .flattenAsFlowable { it }
            .flatMap {
                loadTemperature(it).toFlowable()
            }
            .toList()
}