package com.zalesskyi.android.weatherapp.ui.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class DelayAutoCompleteTextView : AppCompatAutoCompleteTextView {

    companion object {
        private const val AUTO_COMPLETE_DELAY_MS = 500L
    }

    private val searchSubject = PublishSubject.create<String>()
    private var searchDisposable: Disposable? = null
    private var convert: ((Any?) -> CharSequence?)? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        searchDisposable?.dispose()
    }

    fun setCallback(perform: (String) -> Unit) {
        searchDisposable = searchSubject.debounce(AUTO_COMPLETE_DELAY_MS, TimeUnit.MILLISECONDS)
                .subscribe(perform)
    }

    override fun performFiltering(text: CharSequence?, keyCode: Int) {
        searchDisposable?.let {
            searchSubject.onNext(text.toString())
        }
    }

    fun setConverter(convert: (Any?) -> CharSequence?) {
        this.convert = convert
    }

    override fun convertSelectionToString(selectedItem: Any?): CharSequence =
            convert?.invoke(selectedItem) ?: super.convertSelectionToString(selectedItem)
}
