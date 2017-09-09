package com.energolabs.evergo.ui.viewModels

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.energolabs.evergo.R
import com.energolabs.evergo.ui.viewHolders.LoadMoreViewHolder
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem

/**
 * Created by Jose on 9/7/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LoadMoreViewModel(
        private val activity: Activity?
) : AbstractFlexibleItem<LoadMoreViewHolder>() {

    override fun equals(other: Any?): Boolean {
        return other is LoadMoreViewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_load_more_view_progress
    }

    override fun createViewHolder(
            adapter: FlexibleAdapter<*>,
            inflater: LayoutInflater,
            parent: ViewGroup
    ): LoadMoreViewHolder {
        return LoadMoreViewHolder(
                inflater.inflate(
                        layoutRes,
                        parent,
                        false
                ),
                activity,
                adapter
        )
    }

    override fun bindViewHolder(
            adapter: FlexibleAdapter<*>?,
            viewHolder: LoadMoreViewHolder?,
            position: Int,
            payloads: List<*>?
    ) {
        viewHolder?.updateView()
    }

}
