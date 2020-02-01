package com.zalesskyi.android.weatherapp.data.converters.base

abstract class BaseInConverter<IN : Any, OUT : Any> : BaseConverter<IN, OUT>() {
    override fun processConvertOutToIn(outObject: OUT): IN? = null
}
