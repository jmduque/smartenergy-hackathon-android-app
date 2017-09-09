package com.energolabs.evergo.modules.currencyWallet.fragments

import android.os.Bundle
import android.view.View

import com.energolabs.evergo.R
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class WalletWithdrawOngoingFragment : BaseFragment(), View.OnClickListener {

    // VIEWS
    private var btn_return: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_wallet_withdraw_ongoing

    override fun findViews(view: View) {
        btn_return = view.findViewById(R.id.btn_return)
    }

    override fun setListeners() {
        super.setListeners()
        btn_return?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_wallet_withdraw_title)
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

        val RETURN = 1000

        fun makeArguments(
        ): Bundle {
            return Bundle()
        }
    }

}
