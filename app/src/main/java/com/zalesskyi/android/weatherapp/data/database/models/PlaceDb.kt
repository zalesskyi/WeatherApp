package com.zalesskyi.android.weatherapp.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zalesskyi.android.weatherapp.data.database.DatabaseContract

@Entity(tableName = DatabaseContract.PLACES_TABLE_NAME)
data class PlaceDb(var placeId: String?,
                   var name: String?,
                   var lat: Double?,
                   var lng: Double?,
                   var temperature: String?,
                   @PrimaryKey(autoGenerate = true)
                   var id: Long? = null)