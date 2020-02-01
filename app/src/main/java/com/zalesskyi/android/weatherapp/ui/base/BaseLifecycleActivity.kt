package com.zalesskyi.android.weatherapp.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.toast

/**
 * Base activity that implements work with the ViewModel and the life cycle.
 */
@Suppress("LeakingThis")
abstract class BaseLifecycleActivity<T : BaseLifecycleVM> : AppCompatActivity(),
      BaseView  {

    /**
     * Set the Java-class ViewModel.
     */
    abstract val viewModelClass: Class<T>

    /**
     * Set id of the fragment container.
     */
    protected abstract val containerId: Int

    /**
     * Set id of layout.
     */
    protected abstract val layoutId: Int

    private var vProgress: View? = null

    protected abstract fun getProgressBarId(): Int

    protected open fun hasProgressBar(): Boolean = false

    protected open val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(viewModelClass)
    }

    protected open val progressObserver = Observer<Boolean> { isShowProgress ->
        isShowProgress?.let { if (it) showProgress() else hideProgress() }
    }

    protected open val errorObserver = Observer<String> { errMessage ->
        toast(errMessage)
    }

    /**
     * In the method need to subscribe to the LiveData from the [viewModel].
     */
    @CallSuper
    open fun observeLiveData() {
        viewModel.run {
            isLoadingLD.observe(this@BaseLifecycleActivity, progressObserver)
            errorLD.observe(this@BaseLifecycleActivity, errorObserver)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        if (hasProgressBar()) vProgress = findViewById(getProgressBarId())
        observeLiveData()
    }

    override fun showProgress() {
        vProgress?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        vProgress?.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(containerId)?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        supportFragmentManager.findFragmentById(containerId)?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    /**
     * Replace an existing fragment that was added to a container.
     */
    protected open fun replaceFragment(fragment: Fragment, needToAddToBackStack: Boolean = true) {
        val name = fragment.javaClass.simpleName
        with(supportFragmentManager.beginTransaction()) {
            replace(containerId, fragment, name)
            if (needToAddToBackStack) {
                addToBackStack(name)
            }
            commit()
        }
    }

    /**
     * Removes all elements from this back stack.
     */
    protected open fun clearFragmentBackStack() {
        with(supportFragmentManager) {
            (backStackEntryCount - 1 downTo 0)
                .forEach { popBackStack() }
        }
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     */
    protected open fun popBackStackFragment(clazz: Class<*>) =
        supportFragmentManager.findFragmentByTag(clazz.simpleName)?.let {
            supportFragmentManager.popBackStack(clazz.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            true
        } ?: false
}