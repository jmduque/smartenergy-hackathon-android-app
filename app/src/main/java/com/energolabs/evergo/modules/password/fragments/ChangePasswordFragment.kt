package com.energolabs.evergo.modules.password.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.auth.models.AuthModel
import com.energolabs.evergo.modules.password.requests.ChangePasswordRequest
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.energolabs.evergo.utils.ToastUtil

/**
 * Created by Jose Duque on 2/20/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class ChangePasswordFragment : BaseFragment(),
        View.OnClickListener {

    private var et_password: TextView? = null
    private var et_new_password: TextView? = null
    private var et_new_password_repeat: TextView? = null
    private var btn_submit: View? = null

    override val layoutId: Int
        get() = R.layout.fragment_change_password

    override fun findViews(view: View) {
        et_password = view.findViewById(R.id.et_password) as TextView
        et_new_password = view.findViewById(R.id.et_new_password) as TextView
        et_new_password_repeat = view.findViewById(R.id.et_new_password_repeat) as TextView
        btn_submit = view.findViewById(R.id.btn_submit)
    }

    override fun setListeners() {
        super.setListeners()
        btn_submit?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_change_password_title)
    }

    override fun disableViews() {
        et_password?.isEnabled = false
        et_new_password?.isEnabled = false
        et_new_password_repeat?.isEnabled = false
        btn_submit?.isEnabled = false
    }

    override fun enableViews() {
        et_password?.isEnabled = true
        et_new_password?.isEnabled = true
        et_new_password_repeat?.isEnabled = true
        btn_submit?.isEnabled = true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_submit -> {
                onSubmitNewPassword()
            }
        }
    }

    private fun onSubmitNewPassword() {
        val password = et_password?.text.toString()
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToastLong(
                    activity,
                    R.string.energo_change_password_error_missing_current_password
            )
            return
        }

        val newPassword = et_new_password?.text.toString()
        if (TextUtils.isEmpty(newPassword)) {
            ToastUtil.showToastLong(
                    activity,
                    R.string.energo_change_password_error_new_password_dont_match
            )
            return
        }

        val newPasswordRepeat = et_new_password_repeat?.text.toString()
        if (!TextUtils.equals(newPassword, newPasswordRepeat)) {
            ToastUtil.showToastLong(
                    activity,
                    R.string.energo_change_password_error_new_password_dont_match
            )
            return
        }

        ChangePasswordRequest
                .Builder(context ?: return)
                .setPassword(password)
                .setNewPassword(newPassword)
                .setResultListener(changePasswordResultListener)
                .request()
    }

    private val changePasswordResultListener = object : ChangePasswordRequest.ChangePasswordResultListener {

        override fun onResultSuccess(
                tag: Any?,
                response: AuthModel?
        ) {
            DetailActivityNoCollapsing.openWithFragment(
                    context,
                    ChangePasswordSuccessFragment::class.java.name,
                    ChangePasswordSuccessFragment.makeArguments(),
                    true
            )
            activity?.finish()
        }

        override fun onResultError(
                tag: Any?,
                error: String?,
                errorCode: Int
        ) {
            ToastUtil.showToastLong(
                    activity,
                    error
            )
        }

    }

    companion object {

        fun makeArguments(): Bundle? {
            return null
        }
    }

}
