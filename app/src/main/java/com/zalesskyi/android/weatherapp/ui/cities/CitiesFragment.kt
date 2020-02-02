package com.zalesskyi.android.weatherapp.ui.cities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zalesskyi.android.weatherapp.R
import com.zalesskyi.android.weatherapp.data.models.Place
import com.zalesskyi.android.weatherapp.ui.base.BaseLifecycleFragment
import com.zalesskyi.android.weatherapp.ui.cities.adapters.DropdownPlacesAdapter
import com.zalesskyi.android.weatherapp.ui.cities.adapters.PlaceAdapterListener
import com.zalesskyi.android.weatherapp.ui.cities.adapters.PlacesAdapter
import com.zalesskyi.android.weatherapp.utils.EMPTY_STRING
import com.zalesskyi.android.weatherapp.utils.bindInterfaceOrThrow
import kotlinx.android.synthetic.main.fragment_cities.*

class CitiesFragment : BaseLifecycleFragment<CitiesVM>(),
    PlaceAdapterListener {

    companion object {

        fun newInstance() = CitiesFragment()
    }

    override val viewModelClass = CitiesVM::class.java

    override val layoutId = R.layout.fragment_cities

    private var callback: CitiesCallback? = null

    private var dropdownAdapter: DropdownPlacesAdapter? = null
        get() = field ?: DropdownPlacesAdapter(ctx).also {
            field = it
        }

    private var placesAdapter: PlacesAdapter? = null
        get() = field ?: PlacesAdapter(ctx, this).also {
            field = it
        }

    private val predictionsObserver = Observer<List<AutocompletePrediction>> {
        dropdownAdapter?.addAll(it)
    }

    private val newPlaceObserver = Observer<Place> { place ->
        placesAdapter?.run {
            add(place)
            notifyItemInserted(all.lastIndex)
        }
    }

    private val placesObserver = Observer<List<Place>> {
        it.takeIf { it.isNotEmpty() }?.let { places ->
            placesAdapter?.run {
                clear()
                addAll(places)
                notifyDataSetChanged()
            }
        } ?: loadCurrentPlace()
    }

    override fun observeLiveData(viewModel: CitiesVM) {
        viewModel.run {
            predictionsLD.observe(this@CitiesFragment, predictionsObserver)
            newPlaceLD.observe(this@CitiesFragment, newPlaceObserver)
            placesLD.observe(this@CitiesFragment, placesObserver)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = bindInterfaceOrThrow(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        viewModel.loadPlaces()
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }

    override fun onPlaceClick(place: Place) {
        callback?.openDetail(place)
    }

    @SuppressLint("MissingPermission")
    private fun loadCurrentPlace() {
        activity?.let {
            RxPermissions(it)
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe { isGranted ->
                    if (isGranted) viewModel.loadCurrentPlace()
                }
        }
    }

    private fun setupUI() {
        setupSearch()
        setupPlacesList()
    }

    private fun setupSearch() {
        actvSearch?.run {
            setAdapter(dropdownAdapter)
            setConverter { EMPTY_STRING }
            setCallback { viewModel.searchPlaces(it) }
            setOnItemClickListener { _, _, position, _ ->
                dropdownAdapter?.getItem(position)?.placeId?.let { placeId ->
                    viewModel.loadPlace(placeId)
                }
            }
        }
    }

    private fun setupPlacesList() {
        rvPlaces?.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = placesAdapter
        }
    }
}