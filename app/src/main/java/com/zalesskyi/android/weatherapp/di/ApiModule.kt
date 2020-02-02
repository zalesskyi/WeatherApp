package com.zalesskyi.android.weatherapp.di

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.zalesskyi.android.weatherapp.BuildConfig
import com.zalesskyi.android.weatherapp.data.network.api.WeatherApi
import com.zalesskyi.android.weatherapp.data.network.gateways.WeatherGateway
import com.zalesskyi.android.weatherapp.data.network.gateways.WeatherGatewayImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {

    companion object {
        private const val TIMEOUT_MS = 60 * 1000L
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        builder.connectTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)

        return builder.build()
    }

    @Provides
    fun provideMapper(): ObjectMapper =
        ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Provides
    fun provideJackson(mapper: ObjectMapper): JacksonConverterFactory =
        JacksonConverterFactory.create(mapper)

    @Provides
    fun provideRestAdapter(jackson: JacksonConverterFactory, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(jackson)
            .baseUrl(BuildConfig.WEATHER_API_ENDPOINT)
            .client(client)
            .build()

    @Provides
    fun provideWeatherApi(restAdapter: Retrofit): WeatherApi =
        restAdapter.create(WeatherApi::class.java)

    @Provides
    fun provideWeatherGateway(api: WeatherApi): WeatherGateway =
        WeatherGatewayImpl(api)
}