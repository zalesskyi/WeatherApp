package com.zalesskyi.android.weatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.zalesskyi.android.weatherapp.data.database.DatabaseContract.PLACES_TABLE_NAME
import com.zalesskyi.android.weatherapp.data.database.models.PlaceDb
import io.reactivex.Single

@Dao
interface PlacesDao : BaseDao<PlaceDb> {

    @Query("SELECT * FROM $PLACES_TABLE_NAME")
    fun getAll(): Single<List<PlaceDb>>

    @Query("SELECT * FROM $PLACES_TABLE_NAME WHERE placeId=:placeId")
    fun getByPlaceId(placeId: String): Single<PlaceDb>
}