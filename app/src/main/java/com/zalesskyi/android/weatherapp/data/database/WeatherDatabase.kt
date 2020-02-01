package com.zalesskyi.android.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zalesskyi.android.weatherapp.data.database.DatabaseContract.DB_VERSION
import com.zalesskyi.android.weatherapp.data.database.dao.PlacesDao
import com.zalesskyi.android.weatherapp.data.database.models.PlaceDb

@Database(entities = [PlaceDb::class], version = DB_VERSION)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getPlacesDao(): PlacesDao
}