package com.energolabs.evergo.modules.password.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.View

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.auth.models.AuthModel
import com.energolabs.evergo.modules.password.requests.ChangePaymentPasswordRequest
import com.energolabs.evergo.modules.password.requests.ChangePaymentPasswordRequest.ChangePaymentPasswordResultListener
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.energolabs.evergo.utils.ToastUtil

import me.philio.pinentry.PinEntryView

/**
 * Created by Jose Duque on 2/20/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class ChangePaymentPasswordFragment : BaseFragment(), View.OnClickListener {

    private var pev_current_password: PinEntryView? = null
    private var pev_new_password: PinEntryView? = null
    private var pev_new_password_repeat: PinEntryView? = null
    private var btn_submit: View? = null

    override val layoutId: Int
        get() = R.layout.fragment_change_payment_password

    override fun findViews(view: View) {
        pev_current_password = view.findViewById(R.id.pev_current_password) as PinEntryView
        pev_new_password = view.findViewById(R.id.pev_new_password) as PinEntryView
        pev_new_password_repeat = view.findViewById(R.id.pev_new_password_repeat) as PinEntryView
        btn_submit = view.findViewById(R.id.btn_submit)
    }

    override fun setListeners() {
        super.setListeners()
        btn_submit?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_change_payment_password_title)
    }

    override fun disableViews() {
        pev_current_password?.isEnabled = false
        pev_new_password?.isEnabled = false
        pev_new_password_repeat?.isEnabled = false
        btn_submit?.isEnabled = false
    }

    override fun enableViews() {
        pev_current_password?.isEnabled = true
        pev_new_password?.isEnabled = true
        pev_new_password_repeat?.isEnabled = true
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
        val password = pev_current_password?.text.toString()
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToastLong(
                    activity,
                    R.string.energo_change_password_error_missing_current_password
            )
            return
        }

        val newPassword = pev_new_password?.text.toString()
        if (TextUtils.isEmpty(newPassword)) {
            ToastUtil.showToastLong(
                    activity,
                    R.string.energo_change_password_error_new_password_dont_match
            )
            return
        }

        val newPasswordRepeat = pev_new_password_repeat?.text.toString()
        if (!TextUtils.equals(newPassword, newPasswordRepeat)) {
            ToastUtil.showToastLong(
                    activity,
                    R.string.energo_change_password_error_new_password_dont_match
            )
            return
        }

        ChangePaymentPasswordRequest
                .Builder(context ?: return)
                .setPassword(password)
                .setNewPassword(newPassword)
                .setResultListener(changePaymentPasswordResultListener)
                .request()
    }

    private val changePaymentPasswordResultListener = object : ChangePaymentPasswordResultListener {

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
