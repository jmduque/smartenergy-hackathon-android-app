package com.energolabs.evergo.modules.login.fragments

import android.os.Bundle
import android.view.View

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.register.fragments.RegisterFragment
import com.energolabs.evergo.ui.activities.DetailActivityNoToolbar
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class WelcomeFragment : BaseFragment(),
        View.OnClickListener {

    private var tv_sign_in: View? = null
    private var tv_sign_up: View? = null

    override val layoutId: Int
        get() = R.layout.fragment_welcome

    override fun findViews(view: View) {
        tv_sign_in = view.findViewById(R.id.tv_sign_in)
        tv_sign_up = view.findViewById(R.id.tv_sign_up)
    }

    override fun setListeners() {
        super.setListeners()
        tv_sign_in?.setOnClickListener(this)
        tv_sign_up?.setOnClickListener(this)
    }

    override fun disableViews() {
        tv_sign_in?.isEnabled = false
        tv_sign_up?.isEnabled = false
    }

    override fun enableViews() {
        tv_sign_in?.isEnabled = true
        tv_sign_up?.isEnabled = true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_sign_in -> {
                DetailActivityNoToolbar.openWithFragment(
                        context,
                        LoginFragment::class.java.name,
                        LoginFragment.makeArguments(),
                        true
                )
            }
            R.id.tv_sign_up -> {
                DetailActivityNoToolbar.openWithFragment(
                        context,
                        RegisterFragment::class.java.name,
                        RegisterFragment.makeArguments(),
                        true
                )
            }
        }
    }

    companion object {
        fun makeArguments(): Bundle? {
            return null
        }
    }

}
