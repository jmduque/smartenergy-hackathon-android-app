package com.energolabs.evergo.ui.viewModels

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup

import com.energolabs.evergo.models.BaseModel
import com.energolabs.evergo.ui.viewHolders.BaseFlexibleViewHolder

import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem

/**
 * Created by Jose on 9/7/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
abstract class BaseViewModel<
        VH : BaseFlexibleViewHolder<DM>,
        DM : BaseModel
        >(
        protected var activity: Activity?,
        protected var item: DM?
) : AbstractFlexibleItem<VH>() {

    var expanded = false

    override fun equals(o: Any?): Boolean {
        if (o !is BaseViewModel<*, *>) {
            return false
        }

        if (item == null) {
            return false
        }

        val rightItem = o.item
        return item == rightItem
    }

    protected abstract fun makeViewHolder(
            adapter: FlexibleAdapter<*>,
            inflater: LayoutInflater,
            parent: ViewGroup
    ): VH

    override fun createViewHolder(
            adapter: FlexibleAdapter<*>,
            inflater: LayoutInflater,
            parent: ViewGroup
    ): VH {
        return makeViewHolder(
                adapter,
                inflater,
                parent
        )
    }

    override fun bindViewHolder(
            adapter: FlexibleAdapter<*>?,
            viewHolder: VH?,
            position: Int,
            payloads: List<*>?
    ) {
        viewHolder?.item = item
        viewHolder?.updateView()
    }

}
