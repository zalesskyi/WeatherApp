package com.zalesskyi.android.weatherapp

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.zalesskyi.android.weatherapp.data.database.DatabaseCreator
import com.zalesskyi.android.weatherapp.di.*
import java.util.*


class WeatherApp : Application() {

    companion object {

        private const val ENGLISH = "en"

        lateinit var instance: WeatherApp
    }

    lateinit var component: DaggerAppComponent

    override fun onCreate() {
        super.onCreate()
        DatabaseCreator.createDb(this)
        instance = this
        initAppComponent()
        changeLocale()
        Places.initialize(this, getString(R.string.api_key))
    }

    private fun initAppComponent() {
        component = DaggerAppComponent
            .builder()
            .apiModule(ApiModule())
            .repositoriesModule(RepositoriesModule())
            .convertersModule(ConvertersModule())
            .interactorsModule(InteractorsModule())
            .build() as DaggerAppComponent
    }

    /**
     * Need for AutocompletePredictions,
     * so that we can make request to Weather API by place name.
     *
     * Weather API do not support non-english city names.
     */
    private fun changeLocale() {
        baseContext?.resources?.configuration?.let { config ->
            val locale = Locale(ENGLISH)
            Locale.setDefault(locale)
            config.setLocale(locale)
            baseContext.createConfigurationContext(config)
        }
    }
}