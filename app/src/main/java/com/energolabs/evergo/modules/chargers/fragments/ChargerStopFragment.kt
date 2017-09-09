package com.energolabs.evergo.modules.chargers.fragments

import android.os.Bundle
import android.view.View
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.chargers.storage.ChargerPreferences
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 2/7/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class ChargerStopFragment : BaseFragment(),
        View.OnClickListener {

    private var btn_recharge: View? = null

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_charger_title)
    }

    override val layoutId: Int
        get() = R.layout.fragment_charger_stop

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
                ).saveStatus("available")
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