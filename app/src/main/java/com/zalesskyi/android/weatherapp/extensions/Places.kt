package com.zalesskyi.android.weatherapp.extensions

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

fun PlacesClient.findPredictionsSingle(request: FindAutocompletePredictionsRequest) =
    Single.create<List<AutocompletePrediction>> { emitter ->
        findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                emitter.onSuccess(response.autocompletePredictions)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }.observeOn(Schedulers.io())

fun PlacesClient.fetchPlacesSingle(request: FetchPlaceRequest) =
    Single.create<Place> { emitter ->
        fetchPlace(request)
            .addOnSuccessListener {
                emitter.onSuccess(it.place)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }.observeOn(Schedulers.io())