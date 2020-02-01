package com.zalesskyi.android.weatherapp.ui.cities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.zalesskyi.android.weatherapp.R
import com.zalesskyi.android.weatherapp.ui.base.BaseLifecycleFragment
import com.zalesskyi.android.weatherapp.utils.EMPTY_STRING
import kotlinx.android.synthetic.main.fragment_cities.*

class CitiesFragment : BaseLifecycleFragment<CitiesVM>() {

    companion object {

        fun newInstance() = CitiesFragment()
    }

    override val viewModelClass = CitiesVM::class.java

    override val layoutId = R.layout.fragment_cities

    private var placesAdapter: PlaceAdapter? = null
        get() = field ?: PlaceAdapter(requireContext()).also {
            field = it
        }

    private val predictionsObserver = Observer<List<AutocompletePrediction>> {
        placesAdapter?.addAll(it)
    }

    override fun observeLiveData(viewModel: CitiesVM) {
        viewModel.run {
            predictionsLD.observe(this@CitiesFragment, predictionsObserver)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        actvSearch.run {
            setAdapter(placesAdapter)
            setConverter { EMPTY_STRING }
            setCallback { viewModel.searchPlaces(it) }
            setOnItemClickListener { _, _, position, _ ->
                // viewModel.setPlaceId(placesAdapter?.getItem(position)?.placeId.orEmpty())
            }
        }
    }
}