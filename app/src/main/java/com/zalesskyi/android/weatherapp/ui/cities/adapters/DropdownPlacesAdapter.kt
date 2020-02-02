package com.zalesskyi.android.weatherapp.ui.cities.adapters

import android.content.Context
import android.graphics.Typeface
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.zalesskyi.android.weatherapp.R
import org.jetbrains.anko.layoutInflater

class DropdownPlacesAdapter(context: Context) : ArrayAdapter<AutocompletePrediction>(context, 0) {

    companion object {
        private val STYLE_NORMAL = StyleSpan(Typeface.NORMAL)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
                ?: context.layoutInflater.inflate(R.layout.item_dropdown_place, parent, false)
        val item = getItem(position)
        view?.findViewById<TextView>(R.id.tvTitle)?.text = item?.getPrimaryText(STYLE_NORMAL)
        view?.findViewById<TextView>(R.id.tvBody)?.text = item?.getSecondaryText(STYLE_NORMAL)
        return view
    }

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return FilterResults().apply {
                count = getCount()
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) = Unit
    }
}