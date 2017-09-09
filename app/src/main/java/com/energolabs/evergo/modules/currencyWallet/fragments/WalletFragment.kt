package com.energolabs.evergo.modules.currencyWallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.currencyWallet.controllers.CurrencyController
import com.energolabs.evergo.modules.currencyWallet.models.CurrencyWalletModel
import com.energolabs.evergo.modules.currencyWallet.requests.controllers.WalletBalanceController
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 12/13/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class WalletFragment : BaseFragment(),
        View.OnClickListener {

    // CURRENCY WALLET DATA & ACTIONS
    private var tv_currency_balance: TextView? = null
    private var tv_currency_balance_symbol: TextView? = null
    private var rl_recharge: View? = null
    private var rl_withdraw: View? = null

    // API CONTROLLERS
    private var walletBalanceController: WalletBalanceController? = null

    override val layoutId: Int
        get() = R.layout.fragment_wallet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        walletBalanceController = WalletBalanceController(
                context,
                walletBalanceResultListener
        )
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(
                inflater,
                container,
                savedInstanceState
        )
        return view
    }

    override fun findViews(view: View) {
        tv_currency_balance = view.findViewById(R.id.tv_currency_balance) as TextView
        tv_currency_balance_symbol = view.findViewById(R.id.tv_currency_balance_symbol) as TextView
        rl_recharge = view.findViewById(R.id.rl_recharge)
        rl_withdraw = view.findViewById(R.id.rl_withdraw)
    }

    override fun setListeners() {
        super.setListeners()
        rl_recharge?.setOnClickListener(this)
        rl_withdraw?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        walletBalanceController?.makeRequest()
        setTitle(R.string.energo_wallet_title)
    }

    override fun disableViews() {
        tv_currency_balance?.isEnabled = false
        rl_recharge?.isEnabled = false
        rl_withdraw?.isEnabled = false
    }

    override fun enableViews() {
        tv_currency_balance?.isEnabled = true
        rl_recharge?.isEnabled = true
        rl_withdraw?.isEnabled = true
    }

    private val walletBalanceResultListener = object : WalletBalanceController.ResultListener {
        override fun onResultSuccess(
                tag: Any?,
                response: CurrencyWalletModel?
        ) {
            processCurrencyWallet(response)
        }

        override fun onResultError(
                tag: Any?,
                error: String?,
                errorCode: Int
        ) {

        }
    }

    private fun processCurrencyWallet(currencyWalletModel: CurrencyWalletModel?) {
        tv_currency_balance?.text = baseActivity?.getString(
                R.string.energo_currency_format,
                CurrencyController.getRealValue(currencyWalletModel?.balance ?: 0)
        )
        tv_currency_balance_symbol?.text = getString(R.string.energo_wallet_currency_symbol)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.rl_recharge -> {
                DetailActivityNoCollapsing.openWithFragment(
                        context,
                        WalletRechargeFragment::class.java.name,
                        WalletRechargeFragment.makeArguments(
                                tv_currency_balance_symbol?.text.toString()
                        ),
                        true
                )
            }
            R.id.rl_withdraw -> {
                DetailActivityNoCollapsing.openWithFragment(
                        context,
                        WalletWithdrawFragment::class.java.name,
                        WalletWithdrawFragment.makeArguments(
                                tv_currency_balance_symbol?.text.toString()
                        ),
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
