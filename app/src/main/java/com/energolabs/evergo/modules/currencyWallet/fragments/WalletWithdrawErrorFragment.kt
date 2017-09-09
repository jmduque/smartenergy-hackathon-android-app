package com.energolabs.evergo.modules.currencyWallet.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView

import com.energolabs.evergo.R
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class WalletWithdrawErrorFragment : BaseFragment(),
        View.OnClickListener {

    // VIEWS
    private var tv_error_message: TextView? = null
    private var btn_try_again: View? = null

    // DATA
    private var error: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        error = arguments?.getString(ERROR_MESSAGE)
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_wallet_withdraw_error

    override fun findViews(view: View) {
        tv_error_message = view.findViewById(R.id.tv_error_message) as TextView
        btn_try_again = view.findViewById(R.id.btn_try_again)
        updateErrorMessage()
    }

    private fun updateErrorMessage() {
        if (TextUtils.isEmpty(error)) {
            tv_error_message?.setText(R.string.energo_wallet_withdraw_error_message)
        } else {
            tv_error_message?.text = error
        }

    }

    override fun setListeners() {
        super.setListeners()
        btn_try_again?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_wallet_withdraw_title)
    }

    override fun disableViews() {
        btn_try_again?.isEnabled = false
    }

    override fun enableViews() {
        btn_try_again?.isEnabled = true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_try_again -> {
                activity?.setResult(TRY_AGAIN)
                activity?.finish()
            }
        }
    }

    companion object {

        private val ERROR_MESSAGE = "error_message"

        val TRY_AGAIN = 1000

        fun makeArguments(
                error: String?
        ): Bundle {
            val args = Bundle()
            args.putString(
                    ERROR_MESSAGE,
                    error
            )
            return args
        }
    }

}
