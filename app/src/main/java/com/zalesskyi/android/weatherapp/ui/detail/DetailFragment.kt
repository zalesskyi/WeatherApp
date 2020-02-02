package com.zalesskyi.android.weatherapp.ui.detail

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions
import com.zalesskyi.android.weatherapp.R
import com.zalesskyi.android.weatherapp.data.models.Place
import com.zalesskyi.android.weatherapp.ui.base.BaseLifecycleFragment
import com.zalesskyi.android.weatherapp.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseLifecycleFragment<DetailVM>() {

    companion object {

        private const val ZOOM = 10F

        fun newInstance(place: Place) = DetailFragment().apply {
            this.place = place
        }
    }

    override val viewModelClass = DetailVM::class.java

    override val layoutId = R.layout.fragment_detail

    private val onMapReadyCallback = OnMapReadyCallback { map ->
        this.map = map
        setupMap()
    }

    private var place by FragmentArgumentDelegate<Place>()

    private var map: GoogleMap? = null

    override fun observeLiveData(viewModel: DetailVM) = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vGoogleMap?.run {
            onCreate(savedInstanceState)
            getMapAsync(onMapReadyCallback)
        }
    }

    override fun onResume() {
        super.onResume()
        vGoogleMap?.onResume()
    }

    override fun onPause() {
        vGoogleMap?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        vGoogleMap?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        vGoogleMap?.onLowMemory()
    }

    private fun setupMap() {
        place?.latLng?.let { location ->
            val markerOptions = MarkerOptions()
                .position(location)
                .title(generateCurrentLocationTitle())

            map?.run {
                addMarker(markerOptions).showInfoWindow()
                animateCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM))
            }
        }
    }

    private fun generateCurrentLocationTitle() = place?.run {
        "$name ${getString(R.string.temperature, temperature?.toString())}"
    }
}