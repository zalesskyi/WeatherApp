package com.zalesskyi.android.weatherapp.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.NO_ID
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zalesskyi.android.weatherapp.WeatherApp
import com.zalesskyi.android.weatherapp.utils.EMPTY_STRING
import com.zalesskyi.android.weatherapp.utils.bindInterfaceOrThrow
import org.jetbrains.anko.support.v4.toast

/**
 *  Base fragment that implements work with the ViewModel and the life cycle.
 */
abstract class BaseLifecycleFragment<T : BaseLifecycleVM> : Fragment(), BaseView {

    companion object {
        const val NO_TITLE = -1
    }

    /**
     * Set the Java-class ViewModel.
     */
    abstract val viewModelClass: Class<T>

    private var callback: BaseView? = null

    protected open val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(viewModelClass)
    }

    /**
     * Set id of layout.
     */
    protected abstract val layoutId: Int

    protected var toolbar: Toolbar? = null

    protected val ctx: Context
        get() = context ?: WeatherApp.instance

    protected open val progressObserver = Observer<Boolean> {
        if (it == true) showProgress() else hideProgress()
    }

    protected open val errorObserver = Observer<String> { error ->
        error?.let { toast(it) }
    }

    /**
     * In the method need to subscribe to the LiveData from the [viewModel].
     */
    abstract fun observeLiveData(viewModel: T)

    /**
     * Set id of screen title
     *
     * @return Id of screen title
     */
    @StringRes
    protected open fun getScreenTitle(): Int = NO_TITLE

    /**
     * Set if fragment has toolbar
     *
     * @return True if fragment has toolbar
     * False if fragment without toolbar
     */
    protected open fun hasToolbar(): Boolean = false

    /**
     * Set id of toolbar
     *
     * @return Toolbar id
     */
    @IdRes
    protected open fun getToolbarId(): Int = NO_ID

    /**
     * Set if need to show back navigation in toolbar
     *
     * @return True if toolbar has back navigation
     * False if toolbar without back navigation
     */
    protected open fun needToShowBackNav() = true

    /**
     * Set [String] screen title
     *
     * @return Screen title
     */
    protected open fun getStringScreenTitle() =
        if (getScreenTitle() != NO_TITLE) getString(getScreenTitle()) else EMPTY_STRING

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = bindInterfaceOrThrow(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeAllLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutId, container, false)

    override fun onResume() {
        super.onResume()
        initToolbar()
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }

    override fun showProgress() {
        callback?.showProgress()
    }

    override fun hideProgress() {
        callback?.hideProgress()
    }

    /**
     * Initialize toolbar
     */
    private fun initToolbar() {
        view?.apply {
            if (hasToolbar() && getToolbarId() != NO_ID) {
                toolbar = findViewById(getToolbarId())
                with(activity as AppCompatActivity) {
                    setSupportActionBar(toolbar)
                    supportActionBar?.let {
                        setupActionBar(it)
                    }
                }
            }
        }
    }

    /**
     * Setup action bar
     *
     * @param actionBar Modified action bar
     */
    private fun setupActionBar(actionBar: ActionBar) {
        actionBar.apply {
            title = getStringScreenTitle()
            setDisplayHomeAsUpEnabled(needToShowBackNav())
        }
    }

    private fun observeAllLiveData() {
        observeLiveData(viewModel)
        with(viewModel) {
            isLoadingLD.observe(this@BaseLifecycleFragment, progressObserver)
            errorLD.observe(this@BaseLifecycleFragment, errorObserver)
        }
    }

    protected inline fun <reified T> FragmentManager.invokeInterfaceIfExist(block: (item: T) -> Unit) {
        fragments.forEach { if (it is T) block(it) }
    }
}