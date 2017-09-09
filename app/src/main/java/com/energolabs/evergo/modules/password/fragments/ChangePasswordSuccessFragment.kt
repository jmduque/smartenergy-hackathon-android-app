package com.energolabs.evergo.modules.password.fragments

import android.os.Bundle
import android.view.View

import com.energolabs.evergo.R
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 2/20/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class ChangePasswordSuccessFragment : BaseFragment(),
        View.OnClickListener {

    val RETURN = 1000

    private var btn_return: View? = null

    override val layoutId: Int
        get() = R.layout.fragment_change_password_success

    override fun findViews(view: View) {
        btn_return = view.findViewById(R.id.btn_return)
    }

    override fun setListeners() {
        super.setListeners()
        btn_return?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_wallet_recharge_success_title)
    }

    override fun disableViews() {
        btn_return?.isEnabled = false
    }

    override fun enableViews() {
        btn_return?.isEnabled = true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_return -> {
                activity?.setResult(RETURN)
                activity?.finish()
            }
        }
    }

    companion object {

        fun makeArguments(): Bundle? {
            return null
        }
    }
}
