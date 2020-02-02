package com.zalesskyi.android.weatherapp.data.database.repositories

import com.zalesskyi.android.weatherapp.data.converters.db_converters.PlaceDbConverter
import com.zalesskyi.android.weatherapp.data.database.DatabaseCreator
import com.zalesskyi.android.weatherapp.data.database.dao.PlacesDao
import com.zalesskyi.android.weatherapp.data.models.Place
import io.reactivex.Single
import javax.inject.Inject

interface PlacesRepository {

    fun savePlace(place: Place): Single<Place>

    fun savePlaces(places: List<Place>): Single<List<Place>>

    fun getAllPlaces(): Single<List<Place>>

    fun getById(placeId: String): Single<Place>
}

class PlacesRepositoryImpl @Inject constructor(val converter: PlaceDbConverter) : PlacesRepository {

    private val dao: PlacesDao = DatabaseCreator.database.getPlacesDao()

    override fun savePlace(place: Place): Single<Place> =
        Single.just(place)
            .compose(converter.singleINtoOUT())
            .map { dao.insert(it) }
            .map { place }

    override fun savePlaces(places: List<Place>): Single<List<Place>> =
        Single.just(places)
            .compose(converter.listSingleINtoOUT())
            .map { dao.insertAll(it) }
            .map { places }

    override fun getAllPlaces(): Single<List<Place>> =
        dao.getAll()
            .compose(converter.listSingleOUTtoIN())

    override fun getById(placeId: String): Single<Place> =
        dao.getByPlaceId(placeId)
            .compose(converter.singleOUTtoIN())
}