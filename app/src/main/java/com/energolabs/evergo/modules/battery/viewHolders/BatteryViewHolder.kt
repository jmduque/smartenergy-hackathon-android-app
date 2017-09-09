package com.energolabs.evergo.modules.battery.viewHolders

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.battery.fragments.BatteryDetailFragment
import com.energolabs.evergo.modules.battery.models.BatteryModel
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

class BatteryViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder<BatteryModel>(
        view,
        activity,
        flexibleAdapter,
        false
) {

    private var tv_name: TextView? = null
    private var iv_status: ImageView? = null
    private var iv_type: ImageView? = null
    private var tv_capacity: TextView? = null
    private var tv_charge: TextView? = null
    private var progressBar: ProgressBar? = null

    override fun findViews(view: View) {
        super.findViews(view)
        tv_name = view.findViewById(R.id.tv_name) as TextView
        iv_status = view.findViewById(R.id.iv_status) as ImageView
        iv_type = view.findViewById(R.id.iv_type) as ImageView
        tv_capacity = view.findViewById(R.id.tv_capacity) as TextView
        tv_charge = view.findViewById(R.id.tv_charge) as TextView
        progressBar = view.findViewById(R.id.progressBar) as ProgressBar
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
        updateStatus(
                item?.status
        )
        updateType(
                item?.type
        )
        updateCapacity(
                item?.capacity
        )
        updateCharge(
                item?.charge
        )
        updateProgress(
                item?.charge,
                item?.capacity
        )
    }

    private fun updateName(name: String?) {
        tv_name?.text = name ?: activity?.getString(R.string.energo_battery_list_undefined_name)
    }

    private fun updateStatus(status: String?) {
        when (status) {
            "online" -> iv_status?.setImageResource(R.drawable.ic_battery_online)
            else -> iv_status?.setImageResource(R.drawable.ic_battery_offline)
        }
    }

    private fun updateType(type: String?) {
        when (type) {
            "ev" -> iv_type?.visibility = View.VISIBLE
            else -> iv_type?.visibility = View.GONE
        }
    }

    private fun updateCapacity(capacity: Double?) {
        tv_capacity?.text = activity?.getString(
                R.string.energo_energy_format,
                capacity
        )
    }

    private fun updateCharge(charge: Double?) {
        val value = charge ?: 0.0
        tv_charge?.text = activity?.getString(
                R.string.energo_energy_format,
                value
        )
    }

    private fun updateProgress(
            charge: Double?,
            capacity: Double?
    ) {
        progressBar?.progress = charge?.toInt() ?: 0
        progressBar?.max = capacity?.toInt() ?: 100
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            else -> {
                DetailActivityNoCollapsing.openWithFragment(
                        activity ?: return,
                        BatteryDetailFragment::class.java.name,
                        BatteryDetailFragment.makeArguments(
                                item
                        ),
                        true
                )
            }
        }
    }

}