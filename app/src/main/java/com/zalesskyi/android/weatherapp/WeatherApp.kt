package com.zalesskyi.android.weatherapp

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.zalesskyi.android.weatherapp.data.database.DatabaseCreator

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Places.initialize(this, getString(R.string.api_key))
        DatabaseCreator.createDb(this)
    }
}