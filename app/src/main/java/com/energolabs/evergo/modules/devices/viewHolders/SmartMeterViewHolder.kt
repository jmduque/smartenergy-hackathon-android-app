package com.energolabs.evergo.modules.devices.viewHolders

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.devices.fragments.DeviceProfileFragment
import com.energolabs.evergo.modules.devices.models.DeviceInfo
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.viewHolders.BaseFlexibleViewHolder
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class SmartMeterViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder<DeviceInfo>(
        view,
        activity,
        flexibleAdapter,
        false
) {

    private var tv_name: TextView? = null
    private var tv_micro_grid_name: TextView? = null
    private var tv_grid_status: TextView? = null
    private var iv_grid_status_icon: ImageView? = null

    override fun findViews(view: View) {
        super.findViews(view)
        tv_name = view.findViewById(R.id.tv_name) as TextView
        tv_micro_grid_name = view.findViewById(R.id.tv_micro_grid_name) as TextView
        tv_grid_status = view.findViewById(R.id.tv_grid_status) as TextView
        iv_grid_status_icon = view.findViewById(R.id.iv_grid_status_icon) as ImageView
    }

    override fun setListeners() {
        super.setListeners()
        rootView.setOnClickListener(this)
    }

    override fun updateView() {
        super.updateView()
        updateName(
                item?.name
        )

        val userModel = item?.owner
        updateMicroGridName(
                userModel?.gridName
        )

        updateGridStatus(
                item?.gridStatus
        )
    }

    private fun updateName(name: String?) {
        tv_name?.text = name
    }

    private fun updateMicroGridName(gridName: String?) {
        tv_micro_grid_name?.text = gridName
    }

    private fun updateGridStatus(gridStatus: String?) {
        if ("connected" == gridStatus) {
            tv_grid_status?.setText(R.string.energo_smart_meter_device_list_main_grid)
            iv_grid_status_icon?.setImageResource(R.drawable.ic_main_grid)
        } else if ("disconnected" == gridStatus) {
            tv_grid_status?.setText(R.string.energo_smart_meter_device_list_off_grid)
            iv_grid_status_icon?.setImageResource(R.drawable.ic_off_grid)
        }
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            else -> {
                DetailActivityNoCollapsing.openWithFragment(
                        activity ?: return,
                        DeviceProfileFragment::class.java.name,
                        DeviceProfileFragment.makeArguments(
                                item
                        ),
                        true
                )
            }
        }
    }

}
