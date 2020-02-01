package com.zalesskyi.android.weatherapp.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.zalesskyi.android.weatherapp.extensions.doAsync
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * BaseViewModel is a class that is responsible for preparing and managing the data for an Activity or a Fragment.
 */
abstract class BaseLifecycleVM(application: Application) : AndroidViewModel(application) {

    val errorLD = MutableLiveData<String>()
    val isLoadingLD = MediatorLiveData<Boolean>()

    protected open val onErrorConsumer = Consumer<Throwable> {
        errorLD.value = it.message
    }

    private var compositeDisposable: CompositeDisposable? = null

    override fun onCleared() {
        clearSubscription()
        super.onCleared()
    }

    @Suppress("unused")
    protected fun hideProgress() {
        isLoadingLD.value = false
    }

    @Suppress("unused")
    protected fun showProgress() {
        isLoadingLD.value = true
    }

    @Suppress("unused")
    protected fun hideOrShowProgress(hideOrShowFlag: Boolean) {
        isLoadingLD.value = hideOrShowFlag
    }

    /**
     * Add [Disposable] to [CompositeDisposable] to clean up data when ViewModel collapses.
     */
    protected open fun Disposable.addSubscription() = addBackgroundSubscription(this)

    /**
     * Helper methods for working with RxJava
     */
    protected fun <T> Flowable<T>.doAsync(successful: Consumer<T>,
                                          error: Consumer<Throwable> = onErrorConsumer,
                                          isShowProgress: Boolean = true) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
                .addSubscription()
    }

    protected fun <T> Flowable<T>.doAsync(successful: MutableLiveData<T>,
                                          error: Consumer<Throwable> = onErrorConsumer,
                                          isShowProgress: Boolean = true) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
                .addSubscription()
    }

    protected fun <T> Single<T>.doAsync(successful: Consumer<T>,
                                        error: Consumer<Throwable> = onErrorConsumer,
                                        isShowProgress: Boolean = true) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
                .addSubscription()
    }

    protected fun <T> Single<T>.doAsync(successful: MutableLiveData<T>,
                                        error: Consumer<Throwable> = onErrorConsumer,
                                        isShowProgress: Boolean = true) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
                .addSubscription()
    }

    protected fun <T> Observable<T>.doAsync(successful: Consumer<T>,
                                            error: Consumer<Throwable> = onErrorConsumer,
                                            isShowProgress: Boolean = true) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
                .addSubscription()
    }

    protected fun <T> Observable<T>.doAsync(successful: MutableLiveData<T>,
                                            error: Consumer<Throwable> = onErrorConsumer,
                                            isShowProgress: Boolean = true) {
        doAsync(successful, error, isLoadingLD, isShowProgress)
                .addSubscription()
    }

    private fun clearSubscription() {
        compositeDisposable?.apply {
            dispose()
            compositeDisposable = null
        }
    }

    private fun addBackgroundSubscription(subscription: Disposable) {
        compositeDisposable?.apply {
            add(subscription)
        } ?: let {
            compositeDisposable = CompositeDisposable()
            compositeDisposable?.add(subscription)
        }
    }
}