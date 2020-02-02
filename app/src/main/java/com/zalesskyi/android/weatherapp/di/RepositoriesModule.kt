package com.zalesskyi.android.weatherapp.di

import com.zalesskyi.android.weatherapp.data.converters.db_converters.PlaceDbConverter
import com.zalesskyi.android.weatherapp.data.database.repositories.PlacesRepository
import com.zalesskyi.android.weatherapp.data.database.repositories.PlacesRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    fun providePlacesRespository(converter: PlaceDbConverter): PlacesRepository =
        PlacesRepositoryImpl(converter)
}