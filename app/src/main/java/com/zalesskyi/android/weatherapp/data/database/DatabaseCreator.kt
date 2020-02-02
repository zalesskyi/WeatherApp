package com.zalesskyi.android.weatherapp.data.database

import android.content.Context
import androidx.room.Room
import com.zalesskyi.android.weatherapp.data.database.DatabaseContract.DATABASE_NAME


object DatabaseCreator {

    lateinit var database: WeatherDatabase

    fun createDb(context: Context) {
        database = Room.databaseBuilder(context, WeatherDatabase::class.java, DATABASE_NAME).build()
    }
}