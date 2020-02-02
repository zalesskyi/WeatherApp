package com.zalesskyi.android.weatherapp.di

import com.zalesskyi.android.weatherapp.data.database.repositories.PlacesRepository
import com.zalesskyi.android.weatherapp.data.interactors.PlacesInteractor
import com.zalesskyi.android.weatherapp.data.interactors.PlacesInteractorImpl
import com.zalesskyi.android.weatherapp.data.network.gateways.WeatherGateway
import dagger.Module
import dagger.Provides

@Module
class InteractorsModule {

    @Provides
    fun providePlacesInteractor(gateway: WeatherGateway,
                                repository: PlacesRepository): PlacesInteractor =
        PlacesInteractorImpl(gateway, repository)
}