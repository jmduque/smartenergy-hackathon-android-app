package com.energolabs.evergo.modules.devices.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.FragmentPermissionsController
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class DeviceTypesFragment : BaseFragment(), View.OnClickListener {

    private var fl_smart_meter: View? = null
    private var fl_solar_energy: View? = null

    override val layoutId: Int
        get() = R.layout.fragment_device_types

    override fun findViews(view: View) {
        fl_smart_meter = view.findViewById(R.id.fl_smart_meter)
        fl_solar_energy = view.findViewById(R.id.fl_solar_energy)
    }

    override fun setListeners() {
        super.setListeners()
        fl_smart_meter?.setOnClickListener(this)
        fl_solar_energy?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_devices_title)
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fl_smart_meter -> {
                onSmartMeterClicked()
            }
            R.id.fl_solar_energy -> {
                onSolarEnergyClicked()
            }
        }
    }

    private fun onSmartMeterClicked() {
        val permissionsController = FragmentPermissionsController(
                this,
                activity ?: return,
                baseActivity?.main_layout ?: return
        )
        if (!permissionsController.mayUseCamera(
                REQUEST_CAMERA
        )) {
            return
        }

        DetailActivityNoCollapsing.openWithFragment(
                context,
                SmartMeterListFragment::class.java.name,
                SmartMeterListFragment.makeArguments(),
                true
        )
    }

    private fun onSolarEnergyClicked() {

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onSmartMeterClicked()
                }
            }
        }
    }

    companion object {

        private val REQUEST_CAMERA = 100

        fun makeArguments(): Bundle? {
            return null
        }
    }

}
