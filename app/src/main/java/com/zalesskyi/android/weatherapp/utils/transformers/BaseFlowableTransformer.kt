package com.zalesskyi.android.weatherapp.utils.transformers

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Publisher

class BaseFlowableTransformer<T>(val action: (T) -> Flowable<T>) : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> =
            upstream.flatMap { action(it) }
}
