package com.zalesskyi.android.weatherapp.data.database.repositories

import com.zalesskyi.android.weatherapp.data.converters.db_converters.PlaceDbConverter
import com.zalesskyi.android.weatherapp.data.database.dao.PlacesDao
import com.zalesskyi.android.weatherapp.data.models.Place
import io.reactivex.Single

interface PlacesRepository {

    fun savePlace(place: Place): Single<Place>

    fun getAllPlaces(): Single<List<Place>>

    fun getById(placeId: String): Single<Place>
}

class PlacesRepositoryImpl() : PlacesRepository {

    val dao: PlacesDao = TODO()

    val converter: PlaceDbConverter = TODO()

    override fun savePlace(place: Place): Single<Place> =
        Single.just(place)
            .compose(converter.singleINtoOUT())
            .map { dao.insert(it) }
            .map { place }

    override fun getAllPlaces(): Single<List<Place>> =
        dao.getAll()
            .compose(converter.listSingleOUTtoIN())

    override fun getById(placeId: String): Single<Place> =
        dao.getByPlaceId(placeId)
            .compose(converter.singleOUTtoIN())
}