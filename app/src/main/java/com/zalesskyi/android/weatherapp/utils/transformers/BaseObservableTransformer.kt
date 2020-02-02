package com.zalesskyi.android.weatherapp.utils.transformers

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class BaseObservableTransformer<T>(val action: (T) -> Observable<T>) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> =
            upstream.flatMap { action(it) }
}
