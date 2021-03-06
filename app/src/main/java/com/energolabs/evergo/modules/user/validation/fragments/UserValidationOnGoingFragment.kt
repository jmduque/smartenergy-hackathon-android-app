package com.energolabs.evergo.modules.user.validation.fragments

import android.os.Bundle
import android.view.View

import com.energolabs.evergo.R
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class UserValidationOnGoingFragment : BaseFragment(), View.OnClickListener {

    private var btn_done: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_user_validation_ongoing

    override fun findViews(view: View) {
        btn_done = view.findViewById(R.id.btn_done)
    }

    override fun setListeners() {
        super.setListeners()
        btn_done?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_market_ongoing_title)
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_done -> {
                onDoneClicked()
            }
        }
    }

    private fun onDoneClicked() {
        activity?.finish()
    }

    companion object {

        fun makeArguments(): Bundle {
            return Bundle()
        }
    }

}
