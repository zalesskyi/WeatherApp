package com.zalesskyi.android.weatherapp.utils.transformers

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer

class BaseSingleTransformer<T>(val action: (T) -> Single<T>) : SingleTransformer<T?, T> {

    override fun apply(upstream: Single<T?>): SingleSource<T> =
            upstream.flatMap { action(it) }
}
