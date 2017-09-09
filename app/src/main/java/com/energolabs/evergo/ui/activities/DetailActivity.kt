package com.energolabs.evergo.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.ViewsController
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose on 6/17/15.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
open class DetailActivity : BaseActivity(),
        View.OnClickListener {

    protected var actionBar: ActionBar? = null
    private var childBundle: Bundle? = null
    var fragment: BaseFragment? = null
    private var fragment_class: String? = null
    private var withMemory = true

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewsController.hideKeyboard(this, this.currentFocus)
        fragmentManager = supportFragmentManager

        val bundle = intent.extras
        if (bundle != null) {
            fragment_class = bundle.getString(FRAGMENT_CLASS_NAME)
            childBundle = bundle.getParcelable<Bundle>(CHILD_BUNDLE)
            withMemory = bundle.getBoolean(WITH_MEMORY)
        }

        try {
            fragment = Class.forName(fragment_class).newInstance() as BaseFragment
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

        if (childBundle != null) {
            fragment?.arguments = childBundle
        }

        if (withMemory) {
            navigateToFragment(fragment ?: return)
        } else {
            navigateToFragmentNoMemory(fragment ?: return)
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_detail

    override fun findViews() {
        super.findViews()
        main_layout = findViewById(R.id.main_layout) as CoordinatorLayout
    }

    public override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (fragment != null) {
            val handle = fragment?.onOptionsItemSelected(item)
            handle ?: return false
            return true
        }
        return false
    }

    protected fun setTitle(title: String) {
        if (TextUtils.isEmpty(title)) {
            return
        }
        actionBar?.title = title
    }

    override fun setupListeners() {
        super.setupListeners()
        main_layout?.setOnClickListener(this)
        if (toolbar == null) {
            return
        }
        toolbar?.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        invalidateOptionsMenu()
        if (fragmentManager != null && (fragmentManager?.backStackEntryCount ?: 0) > 1) {
            if (currentFragment != null && currentFragment?.onBackPressed() ?: false) {
                return
            }
            goToPreviousFragment()
        } else {
            super.onBackPressed()
        }
    }

    private fun goToPreviousFragment() {
        if (fragmentManager != null) {
            fragmentManager?.popBackStack()
        }
    }

    override val fragmentLayoutId: Int
        get() = R.id.fl_detail

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_layout -> {
                ViewsController.hideKeyboard(this, main_layout)
            }
        }
    }

    companion object {

        val FRAGMENT_CLASS_NAME = "fragment_class_name"
        val WITH_MEMORY = "with_memory"
        val CHILD_BUNDLE = "child_bundle"

        fun openWithFragment(
                context: Context,
                className: String,
                childBundle: Bundle,
                withMemory: Boolean
        ) {
            context.startActivity(
                    makeIntent(
                            context,
                            className,
                            childBundle,
                            withMemory
                    )
            )
        }

        fun openWithFragmentForResult(
                activity: Activity,
                requestCode: Int,
                className: String,
                childBundle: Bundle,
                withMemory: Boolean
        ) {
            activity.startActivityForResult(
                    makeIntent(
                            activity,
                            className,
                            childBundle,
                            withMemory
                    ),
                    requestCode
            )
        }

        fun openWithFragmentForResult(
                fragment: Fragment,
                context: Context,
                requestCode: Int,
                className: String,
                childBundle: Bundle,
                withMemory: Boolean
        ) {
            fragment.startActivityForResult(
                    makeIntent(
                            context,
                            className,
                            childBundle,
                            withMemory
                    ),
                    requestCode
            )
        }

        fun makeIntent(
                context: Context,
                className: String,
                childBundle: Bundle,
                withMemory: Boolean
        ): Intent {
            val intent = Intent(
                    context,
                    DetailActivity::class.java
            )

            val bundle = Bundle()
            bundle.putString(
                    FRAGMENT_CLASS_NAME,
                    className
            )

            bundle.putParcelable(
                    CHILD_BUNDLE,
                    childBundle
            )

            bundle.putBoolean(
                    WITH_MEMORY,
                    withMemory
            )

            intent.putExtras(bundle)
            return intent
        }
    }
}
