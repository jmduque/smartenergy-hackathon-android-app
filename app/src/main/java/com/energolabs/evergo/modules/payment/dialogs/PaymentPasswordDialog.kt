package com.energolabs.evergo.modules.payment.dialogs

import android.app.Activity
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.password.fragments.SetPaymentPasswordFragment
import com.energolabs.evergo.modules.user.profile.storage.UserProfilePreferences
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing

import me.philio.pinentry.PinEntryView

/**
 * Created by Jose Duque on 12/23/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class PaymentPasswordDialog : TextWatcher {

    // WITH PAYMENT PASSWORD
    private var tv_message: TextView? = null
    private var pev_payment_password: PinEntryView? = null
    private var ll_actions: View? = null
    private var btn_cancel: View? = null
    private var btn_confirm: View? = null

    // MISSING PAYMENT PASSWORD
    private var tv_missing_payment_password_message: View? = null
    private var btn_set_payment_password: View? = null

    interface PaymentPasswordListener {

        fun onPaymentPasswordConfirm(
                paymentPassword: String
        )

        fun onPaymentPasswordCancel()

    }

    fun showDialog(
            activity: Activity?,
            root: ViewGroup?,
            @StringRes message: Int,
            paymentPasswordListener: PaymentPasswordListener
    ) {
        activity ?: return
        val builder = AlertDialog.Builder(activity)
        // I'm using fragment here so I'm using getView() to provide ViewGroup
        // but you can provide here any other instance of ViewGroup from your Fragment / Activity
        val viewInflated = LayoutInflater
                .from(activity)
                .inflate(
                        R.layout.dialog_payment_password,
                        root,
                        false
                )
        // Set message text
        tv_message = viewInflated.findViewById(R.id.tv_message) as TextView
        tv_message?.setText(message)

        pev_payment_password = viewInflated.findViewById(R.id.pev_payment_password) as PinEntryView
        pev_payment_password?.addTextChangedListener(
                this
        )

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated)

        ll_actions = viewInflated.findViewById(R.id.ll_actions)
        btn_cancel = viewInflated.findViewById(R.id.btn_cancel)
        btn_confirm = viewInflated.findViewById(R.id.btn_confirm)
        btn_confirm?.isEnabled = false
        val dialog = builder.show()

        btn_cancel?.setOnClickListener { dialog.cancel() }

        btn_confirm?.setOnClickListener {
            paymentPasswordListener.onPaymentPasswordConfirm(
                    pev_payment_password?.text.toString()
            )
            dialog.dismiss()
        }

        dialog.setOnCancelListener { paymentPasswordListener.onPaymentPasswordCancel() }

        tv_missing_payment_password_message = viewInflated.findViewById(R.id.tv_missing_payment_password_message)
        btn_set_payment_password = viewInflated.findViewById(R.id.btn_set_payment_password)
        btn_set_payment_password?.setOnClickListener {
            DetailActivityNoCollapsing.openWithFragment(
                    activity,
                    SetPaymentPasswordFragment::class.java.name,
                    SetPaymentPasswordFragment.makeArguments(),
                    true
            )
            dialog.dismiss()
        }


        val authPreferences = AuthPreferences(activity)
        val userProfilePreferences = UserProfilePreferences(
                activity,
                authPreferences.userId
        )
        if (userProfilePreferences.hasPaymentPassword) {
            initPaymentDialog()
        } else {
            initMissingPaymentDialog()
        }
    }

    private fun initPaymentDialog() {
        tv_message?.visibility = View.VISIBLE
        pev_payment_password?.visibility = View.VISIBLE
        ll_actions?.visibility = View.VISIBLE

        tv_missing_payment_password_message?.visibility = View.GONE
        btn_set_payment_password?.visibility = View.GONE
    }

    private fun initMissingPaymentDialog() {
        tv_message?.visibility = View.GONE
        pev_payment_password?.visibility = View.GONE
        ll_actions?.visibility = View.GONE

        tv_missing_payment_password_message?.visibility = View.VISIBLE
        btn_set_payment_password?.visibility = View.VISIBLE
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        btn_confirm?.isEnabled = s.length >= 6
    }

}
