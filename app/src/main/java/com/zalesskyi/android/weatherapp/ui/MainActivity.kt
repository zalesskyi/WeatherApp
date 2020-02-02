package com.zalesskyi.android.weatherapp.ui

import android.os.Bundle
import android.view.View.NO_ID
import com.zalesskyi.android.weatherapp.R
import com.zalesskyi.android.weatherapp.data.models.Place
import com.zalesskyi.android.weatherapp.ui.base.BaseLifecycleActivity
import com.zalesskyi.android.weatherapp.ui.cities.CitiesCallback
import com.zalesskyi.android.weatherapp.ui.cities.CitiesFragment
import com.zalesskyi.android.weatherapp.ui.detail.DetailFragment

class MainActivity : BaseLifecycleActivity<MainVM>(), CitiesCallback {

    override val viewModelClass = MainVM::class.java

    override val containerId = R.id.flContainer

    override val layoutId = R.layout.activity_main

    override fun getProgressBarId() = NO_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(CitiesFragment.newInstance(), false)
    }

    override fun openDetail(place: Place) {
        replaceFragment(DetailFragment.newInstance(place), true)
    }
}