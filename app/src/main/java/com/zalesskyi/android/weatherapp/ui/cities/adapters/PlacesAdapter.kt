package com.zalesskyi.android.weatherapp.ui.cities.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zalesskyi.android.weatherapp.data.models.Place
import com.zalesskyi.android.weatherapp.databinding.ItemPlaceBinding
import com.zalesskyi.android.weatherapp.ui.base.BaseRecyclerViewAdapter
import java.lang.ref.WeakReference

class PlacesAdapter(context: Context, listener: PlaceAdapterListener) : BaseRecyclerViewAdapter<Place, PlacesAdapter.PlaceHolder>(context) {

    private val listenerRef = WeakReference(listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder =
        PlaceHolder.newInstance(inflater, parent, listenerRef.get())

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.bind(getItem(position))
     }

    class PlaceHolder(view: View,
                      private val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(view) {

        companion object {

            fun newInstance(inflater: LayoutInflater, parent: ViewGroup, listener: PlaceAdapterListener?) =
                ItemPlaceBinding.inflate(inflater, parent, false).let { binding ->
                    listener?.let {
                        binding.listener = it
                    }
                    PlaceHolder(binding.root, binding)
                }
    }

        fun bind(place: Place) {
            binding.run {
                setPlace(place)
                executePendingBindings()
            }
        }
    }
}