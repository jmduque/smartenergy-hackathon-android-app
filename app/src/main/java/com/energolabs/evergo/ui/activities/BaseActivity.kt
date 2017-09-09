package com.energolabs.evergo.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Pair
import android.view.View
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.ViewsController
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by JoseMiguel on 6/10/2015.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected var fragmentManager: FragmentManager? = null

    protected var currentFragment: BaseFragment? = null
    var main_layout: View? = null
        protected set
    var toolbar: Toolbar? = null

    protected var waitDialog: ProgressDialog? = null
    protected var baseActivity: BaseActivity? = null

    protected var isActivityVisible: Boolean = false

    protected fun showWaitDialog() {
        if (waitDialog == null) {
            waitDialog = ProgressDialog(
                    this
            )
            waitDialog?.setMessage(getString(R.string.str_wait))
            waitDialog?.setCancelable(false)
        }

        waitDialog?.show()
    }

    protected fun hideWaitDialog() {
        if (waitDialog == null) {
            return
        }

        if (!(waitDialog?.isShowing ?: false)) {
            return
        }
        waitDialog?.dismiss()
    }


    abstract val layoutId: Int

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }


    fun setMain_layout(main_layout: CoordinatorLayout) {
        this.main_layout = main_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = this
        fragmentManager = supportFragmentManager
        setContentView(layoutId)
        findViews()
        setupListeners()
    }

    override fun onBackPressed() {
        if (currentFragment == null) {
            finish()
            return
        }
        if (!(currentFragment?.onBackPressed() ?: false)) {
            finish()
            super.onBackPressed()
        }
    }

    @CallSuper
    protected open fun findViews() {
        main_layout = findViewById(R.id.main_layout)
        toolbar = findViewById(R.id.toolbar) as Toolbar?
        toolbar ?: return
        setSupportActionBar(toolbar)
    }

    @CallSuper
    protected open fun setupListeners() {
    }

    override fun onResume() {
        super.onResume()
        isActivityVisible = true
    }

    override fun onPause() {
        super.onPause()
        isActivityVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @CallSuper
    protected fun disableViews() {
    }

    @CallSuper
    protected fun enableViews() {
    }

    open val fragmentLayoutId: Int
        get() = -1

    fun navigateBackToFragment(tag: String): Boolean {
        if (fragmentManager == null) {
            return false
        }

        val backFragment = fragmentManager?.findFragmentByTag(tag) ?: return false

        if (backFragment == currentFragment) {
            return true
        }

        navigateToFragment(
                backFragment
        )
        return true
    }

    /**
     * @param fragment
     * *
     * @author Jose Duque
     */
    fun navigateToFragment(fragment: Fragment) {
        navigateToFragment(
                fragment, null, null,
                true
        )
    }

    /**
     * @param fragment
     * *
     * @author Jose Duque
     */
    fun navigateToFragment(
            fragment: Fragment,
            tag: String
    ) {
        navigateToFragment(
                fragment, null,
                tag,
                true
        )
    }

    fun navigateToFragmentNoMemory(fragment: Fragment) {
        navigateToFragment(
                fragment, null, null,
                false
        )
    }

    protected fun navigateToFragment(
            fragment: Fragment?,
            transitions: List<Pair<View, String>>?,
            tag: String?,
            memory: Boolean
    ) {
        if (fragment == null) {
            return
        }

        if (fragmentManager == null) {
            return
        }


        ViewsController.hideKeyboard(this, this.currentFocus)

        this.currentFragment = (fragment as BaseFragment?)
        val transaction = fragmentManager
                ?.beginTransaction()
                ?.replace(
                        fragmentLayoutId,
                        fragment,
                        tag
                )

        if (memory) {
            transaction?.addToBackStack(fragment.id.toString())
        }

        transitions
                ?.indices
                ?.map { transitions[it] }
                ?.forEach {
                    transaction?.addSharedElement(
                            it.first,
                            it.second
                    )
                }

        transaction?.commit()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }

}