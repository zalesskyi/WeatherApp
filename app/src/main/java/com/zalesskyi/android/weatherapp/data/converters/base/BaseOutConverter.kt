package com.zalesskyi.android.weatherapp.data.converters.base

abstract class BaseOutConverter<IN : Any, OUT : Any> : BaseConverter<IN, OUT>() {
    override fun processConvertInToOut(inObject: IN): OUT? = null
}
