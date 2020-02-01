package com.zalesskyi.android.weatherapp.data.database

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.zalesskyi.android.weatherapp.data.database.DatabaseContract.DATABASE_NAME
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicBoolean


object DatabaseCreator {
    @Suppress("unused")
    val isDatabaseCreated = MutableLiveData<Boolean>()

    lateinit var database: WeatherDatabase

    private val mInitializing = AtomicBoolean(true)

    fun createDb(context: Context) {
        if (mInitializing.compareAndSet(true, false).not()) {
            return
        }
        isDatabaseCreated.value = false
        Completable.fromAction {
            database = Room.databaseBuilder(context, WeatherDatabase::class.java, DATABASE_NAME).build()
        }.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isDatabaseCreated.value = true }, {  }).dispose()
    }
}