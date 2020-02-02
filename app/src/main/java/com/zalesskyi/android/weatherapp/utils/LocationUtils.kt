package com.zalesskyi.android.weatherapp.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context.LOCATION_SERVICE
import android.location.Geocoder
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import androidx.annotation.RequiresPermission
import com.google.android.gms.maps.model.LatLng
import com.zalesskyi.android.weatherapp.CURRENT_PLACE_ID
import com.zalesskyi.android.weatherapp.WeatherApp
import com.zalesskyi.android.weatherapp.data.models.Place
import com.zalesskyi.android.weatherapp.data.models.PlaceModel
import java.util.*

object LocationUtils {

    private const val GEOCODER_MAX_RESULTS = 1
    private const val CITY_INDEX = 0

    /**
     * Get [Place] by current coordinates.
     *
     * @return current [Place].
     */
    @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
    fun getCurrentPlace(): Place? =
        (WeatherApp.instance.getSystemService(LOCATION_SERVICE) as? LocationManager)?.run {
            val provider = NETWORK_PROVIDER.takeIf { isProviderEnabled(NETWORK_PROVIDER) } ?: GPS_PROVIDER
            getLastKnownLocation(provider)
        }?.run {
            Geocoder(WeatherApp.instance,
                Locale.getDefault()).getFromLocation(latitude, longitude, GEOCODER_MAX_RESULTS)
                ?.firstOrNull()?.locality?.let { cityName ->
                    PlaceModel(id = CURRENT_PLACE_ID,
                        name = cityName,
                        latLng = LatLng(latitude, longitude))
                }
        }
}