package com.energolabs.evergo.modules.devices.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.devices.models.DeviceInfo
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.energolabs.evergo.utils.ToastUtil

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

 class SmartMeterBindingNameFragment : BaseFragment(),
        View.OnClickListener {

    private var et_name: TextView? = null
    private var btn_next: View? = null

    private var deviceInfo: DeviceInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        deviceInfo = arguments.getSerializable(DEVICE_INFO) as DeviceInfo
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_smart_meter_binding_name

    override fun findViews(view: View) {
        et_name = view.findViewById(R.id.et_name) as TextView
        btn_next = view.findViewById(R.id.btn_next)
    }

    override fun setListeners() {
        super.setListeners()
        btn_next?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_smart_meter_binding_title)
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_next -> {
                onNextClicked()
            }
        }
    }

    private fun onNextClicked() {
        val name = et_name?.text.toString()
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToastLong(
                    activity,
                    R.string.energo_smart_meter_binding_error_name
            )
            return
        }

        deviceInfo?.name = name

        DetailActivityNoCollapsing.openWithFragment(
                context,
                SmartMeterBindingLocationFragment::class.java.name,
                SmartMeterBindingLocationFragment.makeArguments(
                        deviceInfo
                ),
                true
        )
        activity?.finish()
    }

    companion object {

        private val DEVICE_INFO = "device_info"

        fun makeArguments(
                deviceInfo: DeviceInfo?
        ): Bundle {
            val args = Bundle()
            args.putSerializable(
                    DEVICE_INFO,
                    deviceInfo
            )
            return args
        }
    }

}
