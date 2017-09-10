package com.energolabs.evergo.modules.battery.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.battery.models.BatteryModel
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 12/23/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class BatteryDetailFragment : BaseFragment() {

    private var et_name: TextView? = null
    private var et_capacity: TextView? = null
    private var et_charge: TextView? = null
    private var et_type: TextView? = null
    private var et_status: TextView? = null
    private var et_location: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_battery_detail

    override fun findViews(view: View) {
        et_name = view.findViewById(R.id.et_name) as TextView
        et_capacity = view.findViewById(R.id.et_capacity) as TextView
        et_charge = view.findViewById(R.id.et_charge) as TextView
        et_type = view.findViewById(R.id.et_type) as TextView
        et_status = view.findViewById(R.id.et_status) as TextView
        et_location = view.findViewById(R.id.et_location) as TextView

        updateBattery(
                arguments?.getSerializable(ITEM) as BatteryModel?
        )
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_battery_title)
    }

    private fun updateBattery(
            item: BatteryModel?
    ) {
        updateName(item?.name)
        updateCapacity(item?.capacity ?: 0.0)
        updateCharge(item?.charge ?: 0.0)
        updateType(item?.type)
        updateStatus(item?.status)
        updateLocation(
                item?.location?.name ?: getString(R.string.energo_battery_list_undefined_name),
                item?.location?.latitude ?: 0.0,
                item?.location?.longitude ?: 0.0
        )
    }

    private fun updateName(
            name: String?
    ) {
        et_name?.text = name
    }

    private fun updateCapacity(
            capacity: Double
    ) {
        et_capacity?.text = getString(
                R.string.energo_energy_format,
                capacity
        )
    }

    private fun updateCharge(
            charge: Double
    ) {
        et_charge?.text = getString(
                R.string.energo_energy_format,
                charge
        )
    }

    private fun updateType(type: String?) {
        et_type?.text = type
    }

    private fun updateStatus(
            status: String?
    ) {
        et_status?.text = status
    }

    private fun updateLocation(
            name: String,
            latitude: Double,
            longitude: Double
    ) {
        et_location?.text = name +
                '\n' +
                getString(R.string.energo_energy_format, latitude) +
                ", " +
                getString(R.string.energo_energy_format, longitude)
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    companion object {

        val ITEM: String = "ITEM"

        fun makeArguments(
                item: BatteryModel?
        ): Bundle? {
            val args = Bundle()
            args.putSerializable(
                    ITEM,
                    item
            )
            return args
        }
    }

}