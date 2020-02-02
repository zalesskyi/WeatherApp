package com.zalesskyi.android.weatherapp.di

import com.zalesskyi.android.weatherapp.data.converters.db_converters.PlaceDbConverter
import com.zalesskyi.android.weatherapp.data.converters.db_converters.PlaceDbConverterImpl
import dagger.Module
import dagger.Provides

@Module
class ConvertersModule {

    @Provides
    fun providePlaceDbConverter(): PlaceDbConverter =
        PlaceDbConverterImpl()
}