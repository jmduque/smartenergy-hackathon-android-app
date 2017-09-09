package com.energolabs.evergo.modules.location.viewModels

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.location.models.LocationModel
import com.energolabs.evergo.modules.location.viewHolders.LocationModelViewHolder
import com.energolabs.evergo.ui.viewModels.BaseViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/24/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LocationModelViewModel(
        activity: Activity?,
        item: LocationModel?
) : BaseViewModel<LocationModelViewHolder, LocationModel>(
        activity,
        item
) {

    override fun getLayoutRes(): Int {
        return R.layout.item_location
    }

    override fun makeViewHolder(
            adapter: FlexibleAdapter<*>,
            inflater: LayoutInflater,
            parent: ViewGroup
    ): LocationModelViewHolder {
        return LocationModelViewHolder(
                inflater.inflate(
                        layoutRes,
                        parent,
                        false
                ),
                activity,
                adapter
        )
    }

}