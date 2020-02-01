package com.zalesskyi.android.weatherapp.data.base

import android.os.Parcelable

interface Model<T> : Parcelable {
    var id: T?
}