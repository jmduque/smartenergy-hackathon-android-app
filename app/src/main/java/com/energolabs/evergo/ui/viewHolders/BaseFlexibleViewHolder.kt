package com.energolabs.evergo.ui.viewHolders

import android.app.Activity
import android.content.res.Resources
import android.view.View

import com.energolabs.evergo.ui.viewModels.BaseViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder

/**
 * Created by JoseMiguel on 6/8/2015.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
open class BaseFlexibleViewHolder<DM>(
        // VIEWS
        protected var rootView: View,
        // CONTEXT
        protected var activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>,
        stickyHeader: Boolean = false
) : FlexibleViewHolder(
        rootView,
        flexibleAdapter,
        stickyHeader
) {

    protected var resources: Resources? = activity?.resources

    // REFERENCE VIEW MODEL
    var viewModel: BaseViewModel<*, *>? = null

    // DATA
    open var item: DM? = null

    init {
        findViews(rootView)
        setListeners()
    }

    protected open fun findViews(view: View) {}

    protected open fun setListeners() {

    }

    open fun updateView() {

    }
}
