package com.zalesskyi.android.weatherapp.di

import com.zalesskyi.android.weatherapp.ui.cities.CitiesVM
import dagger.Component

@Component(modules = [ApiModule::class, ConvertersModule::class, RepositoriesModule::class, InteractorsModule::class])
interface AppComponent {

    fun inject(viewModel: CitiesVM)
}