package com.zalesskyi.android.weatherapp.ui.base

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zalesskyi.android.weatherapp.WeatherApp

/**
 * Base adapter for recycler view
 */
abstract class BaseRecyclerViewAdapter<TData, TViewHolder : RecyclerView.ViewHolder>
(context: Context?, data: List<TData> = listOf()) : RecyclerView.Adapter<TViewHolder>() {

    protected val appContext = context ?: WeatherApp.instance
    protected val inflater: LayoutInflater = LayoutInflater.from(appContext)
    protected val data: MutableList<TData> = data.toMutableList()

    override fun getItemCount() = data.size

    @Throws(ArrayIndexOutOfBoundsException::class)
    open fun getItem(position: Int) = data[position]

    fun add(collection: TData) = data.add(collection)

    fun add(oldPosition: Int, newPosition: Int) = data.add(newPosition, remove(oldPosition))

    operator fun set(position: Int, collection: TData) = data.set(position, collection)

    open fun remove(item: TData) = data.remove(item)

    fun remove(position: Int) = data.removeAt(position)

    @Suppress("unused")
    fun updateListItems(newObjects: List<TData>, callback: DiffUtil.Callback) {
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newObjects)
    }

    val all: List<TData>
        get() = data

    fun clear() {
        data.clear()
    }

    fun clearAndNotify() {
        clear()
        notifyDataSetChanged()
    }

    fun addAll(collection: Collection<TData>) = data.addAll(collection)

    fun updateAllNotify(collection: Collection<TData>) {
        clear()
        addAll(collection)
        notifyDataSetChanged()
    }

    val snapshot: List<TData>
        get() = data.toMutableList()

    @Suppress("unused")
    fun getItemPosition(collection: TData) = data.indexOf(collection)

    fun insert(collection: TData, position: Int) = data.add(position, collection)

    fun insertAll(collection: Collection<TData>, position: Int) = data.addAll(position, collection)

    fun isEmpty() = itemCount == 0

    fun isNotEmpty() = itemCount > 0

}
