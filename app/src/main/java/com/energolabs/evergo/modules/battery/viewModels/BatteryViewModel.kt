package com.energolabs.evergo.modules.battery.viewModels

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.battery.models.BatteryModel
import com.energolabs.evergo.modules.battery.viewHolders.BatteryViewHolder
import com.energolabs.evergo.ui.viewModels.BaseViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class BatteryViewModel(
        activity: Activity?,
        item: BatteryModel?
) : BaseViewModel<BatteryViewHolder, BatteryModel>(
        activity,
        item
) {

    override fun getLayoutRes(): Int {
        return R.layout.item_battery
    }

    override fun makeViewHolder(
            adapter: FlexibleAdapter<*>,
            inflater: LayoutInflater,
            parent: ViewGroup
    ): BatteryViewHolder {
        return BatteryViewHolder(
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