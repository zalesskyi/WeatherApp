package com.zalesskyi.android.weatherapp.utils

inline fun <reified T> bindInterfaceOrThrow(vararg objects: Any?): T =
      objects.find { it is T }
            ?.let { it as T }
            ?: throw RuntimeException("You have to implement ${T::class.java}")