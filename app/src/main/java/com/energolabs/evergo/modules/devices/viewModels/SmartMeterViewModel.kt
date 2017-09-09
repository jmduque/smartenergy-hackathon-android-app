package com.energolabs.evergo.modules.devices.viewModels

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.devices.models.DeviceInfo
import com.energolabs.evergo.modules.devices.viewHolders.SmartMeterViewHolder
import com.energolabs.evergo.ui.viewModels.BaseViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class SmartMeterViewModel(
        activity: Activity?,
        item: DeviceInfo?
) : BaseViewModel<SmartMeterViewHolder, DeviceInfo>(
        activity,
        item
) {

    override fun getLayoutRes(): Int {
        return R.layout.item_device_list_smart_meter
    }

    override fun makeViewHolder(
            adapter: FlexibleAdapter<*>,
            inflater: LayoutInflater,
            parent: ViewGroup
    ): SmartMeterViewHolder {
        return SmartMeterViewHolder(
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
