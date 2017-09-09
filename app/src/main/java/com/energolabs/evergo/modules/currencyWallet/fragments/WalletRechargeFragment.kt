package com.energolabs.evergo.modules.currencyWallet.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.currencyWallet.controllers.CurrencyController
import com.energolabs.evergo.modules.currencyWallet.models.CurrencyWalletModel
import com.energolabs.evergo.modules.currencyWallet.requests.controllers.WalletExchangeController
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 12/14/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class WalletRechargeFragment : BaseFragment(),
        View.OnClickListener,
        WalletExchangeController.ResultListener {

    // ARGUMENTS DATA
    private var currencySymbol: String? = null

    // VIEWS
    private var iv_alipay: View? = null
    private var iv_paypal: View? = null

    private var et_currency_amount: TextView? = null
    private var btn_recharge: View? = null

    // API CONTROLLERS
    private var walletExchangeController: WalletExchangeController? = null

    override val layoutId: Int
        get() = R.layout.fragment_wallet_recharge

    override fun onCreate(savedInstanceState: Bundle?) {
        val args = arguments
        currencySymbol = args?.getString(CURRENCY_SYMBOL)
        super.onCreate(savedInstanceState)
        walletExchangeController = WalletExchangeController(
                context,
                this
        )
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_wallet_recharge_title)
    }

    override fun findViews(view: View) {
        iv_alipay = view.findViewById(R.id.iv_alipay)
        iv_alipay?.isSelected = true
        iv_paypal = view.findViewById(R.id.iv_paypal)

        val tv_currency_symbol = view.findViewById(R.id.tv_currency_symbol) as TextView
        tv_currency_symbol.text = currencySymbol
        et_currency_amount = view.findViewById(R.id.et_currency_amount) as TextView

        btn_recharge = view.findViewById(R.id.btn_recharge)
    }

    override fun setListeners() {
        super.setListeners()
        iv_alipay?.setOnClickListener(this)
        iv_paypal?.setOnClickListener(this)
        btn_recharge?.setOnClickListener(this)
    }

    override fun disableViews() {
        iv_alipay?.isEnabled = false
        iv_paypal?.isEnabled = false
        et_currency_amount?.isEnabled = false
        btn_recharge?.isEnabled = false
    }

    override fun enableViews() {
        iv_alipay?.isEnabled = true
        iv_paypal?.isEnabled = true
        et_currency_amount?.isEnabled = true
        btn_recharge?.isEnabled = true
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_alipay -> {
                iv_alipay?.isSelected = true
                iv_paypal?.isSelected = false
            }
            R.id.iv_paypal -> {
                iv_alipay?.isSelected = false
                iv_paypal?.isSelected = true
            }
            R.id.btn_recharge -> {
                val amountText = et_currency_amount?.text?.toString() ?: "0.0"
                if (amountText.isEmpty()) {
                    showMessageDialog(
                            activity,
                            R.string.energo_wallet_recharge_error_invalid_amount,
                            R.string.energo_wallet_recharge_error_title,
                            null
                    )
                    return
                }

                val amount = java.lang.Double.valueOf(
                        amountText
                )
                walletExchangeController
                        ?.setType(WalletExchangeController.TYPE_TOPUP)
                        ?.setCurrencySymbol(currencySymbol)
                        ?.setAmount(
                                CurrencyController.getRawValue(amount)
                        )
                        ?.makeRequest()
            }
        }
    }

    override fun onResultSuccess(
            tag: Any?,
            response: CurrencyWalletModel?
    ) {
        DetailActivityNoCollapsing.openWithFragmentForResult(
                this,
                context,
                SUCCESS,
                WalletRechargeSuccessFragment::class.java.name,
                WalletRechargeSuccessFragment.makeArguments(
                        walletExchangeController?.amount ?: 0,
                        walletExchangeController?.currencySymbol
                ),
                true
        )
    }

    override fun onResultError(
            tag: Any?,
            error: String?,
            errorCode: Int
    ) {
        DetailActivityNoCollapsing.openWithFragmentForResult(
                this,
                context,
                ERROR,
                WalletRechargeErrorFragment::class.java.name,
                WalletRechargeErrorFragment.makeArguments(
                        error
                ),
                true
        )
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        when (requestCode) {
            SUCCESS -> {
                if (resultCode == WalletWithdrawOngoingFragment.RETURN) {
                    activity?.onBackPressed()
                }
            }
            ERROR -> {
                if (resultCode == WalletWithdrawErrorFragment.TRY_AGAIN) {
                    btn_recharge?.callOnClick()
                }
            }
            else -> {
                super.onActivityResult(
                        requestCode,
                        resultCode,
                        data
                )
            }
        }
    }

    companion object {

        private val CURRENCY_SYMBOL = "currency_symbol"
        private val SUCCESS = 500
        private val ERROR = 1000

        fun makeArguments(currencySymbol: String): Bundle {
            val args = Bundle()

            args.putString(
                    CURRENCY_SYMBOL,
                    currencySymbol
            )

            return args
        }
    }
}
