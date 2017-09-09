package com.energolabs.evergo.modules.chargers.fragments

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.chargers.storage.ChargerPreferences
import com.energolabs.evergo.modules.devices.fragments.SmartMeterBindingNameFragment
import com.energolabs.evergo.modules.devices.models.DeviceInfo
import com.energolabs.evergo.modules.devices.requests.GetDeviceFromQRRequest
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

/**
 * Created by Jose Duque on 2/7/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class ChargerStartFragment : BaseFragment(),
        View.OnClickListener {

    private var btn_recharge: View? = null

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_charger_title)
    }

    override val layoutId: Int
        get() = R.layout.fragment_charger_start

    override fun findViews(view: View) {
        btn_recharge = view.findViewById(R.id.btn_recharge)
    }

    override fun setListeners() {
        super.setListeners()
        btn_recharge?.setOnClickListener(this)
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_recharge -> {
                ChargerPreferences(
                        activity,
                        AuthPreferences(activity).userId
                ).saveStatus("charging")
                activity.finish()
            }
        }
    }

    companion object {
        fun makeArguments(): Bundle? {
            return null
        }
    }

}