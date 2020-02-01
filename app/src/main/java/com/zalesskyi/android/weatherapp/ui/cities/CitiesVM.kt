package com.zalesskyi.android.weatherapp.ui.cities

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.zalesskyi.android.weatherapp.ui.base.BaseLifecycleVM
import io.reactivex.Single

class CitiesVM(application: Application) : BaseLifecycleVM(application) {

    val predictionsLD = MutableLiveData<List<AutocompletePrediction>>()

    private val placesClient = Places.createClient(application)

    fun searchPlaces(query: String) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setSessionToken(AutocompleteSessionToken.newInstance())
            .setQuery(query)
            .setTypeFilter(TypeFilter.CITIES)
            .build()

        Single.create<List<AutocompletePrediction>> { emitter ->
            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    emitter.onSuccess(response.autocompletePredictions)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }.doAsync(predictionsLD)
    }
}