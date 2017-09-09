package com.energolabs.evergo.modules.currencyWallet.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.currencyWallet.controllers.CurrencyController
import com.energolabs.evergo.modules.currencyWallet.models.CurrencyWalletModel
import com.energolabs.evergo.modules.currencyWallet.requests.controllers.WalletExchangeController
import com.energolabs.evergo.modules.payment.dialogs.PaymentPasswordDialog
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 12/14/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class WalletWithdrawFragment : BaseFragment(),
        View.OnClickListener,
        WalletExchangeController.ResultListener {

    // ARGUMENTS DATA
    private var currencySymbol: String? = null

    // VIEWS
    private var iv_alipay: View? = null
    private var iv_paypal: View? = null

    private var et_currency_amount: TextView? = null
    private var btn_withdraw: View? = null

    // API CONTROLLERS
    private var walletExchangeController: WalletExchangeController? = null

    override val layoutId: Int
        get() = R.layout.fragment_wallet_withdraw

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
        setTitle(R.string.energo_wallet_withdraw_title)
    }

    override fun findViews(view: View) {
        iv_alipay = view.findViewById(R.id.iv_alipay)
        iv_alipay?.isSelected = true
        iv_paypal = view.findViewById(R.id.iv_paypal)

        val tv_currency_symbol = view.findViewById(R.id.tv_currency_symbol) as TextView
        tv_currency_symbol.text = currencySymbol
        et_currency_amount = view.findViewById(R.id.et_currency_amount) as TextView

        btn_withdraw = view.findViewById(R.id.btn_withdraw)
    }

    override fun setListeners() {
        super.setListeners()
        iv_alipay?.setOnClickListener(this)
        iv_paypal?.setOnClickListener(this)
        btn_withdraw?.setOnClickListener(this)
    }

    override fun disableViews() {
        iv_alipay?.isEnabled = false
        iv_paypal?.isEnabled = false
        et_currency_amount?.isEnabled = false
        btn_withdraw?.isEnabled = false
    }

    override fun enableViews() {
        iv_alipay?.isEnabled = true
        iv_paypal?.isEnabled = true
        et_currency_amount?.isEnabled = true
        btn_withdraw?.isEnabled = true
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
            R.id.btn_withdraw -> {
                PaymentPasswordDialog()
                        .showDialog(
                                activity,
                                getView() as ViewGroup?,
                                R.string.energo_wallet_withdraw_alipay_fee,
                                object : PaymentPasswordDialog.PaymentPasswordListener {
                                    override fun onPaymentPasswordConfirm(paymentPassword: String) {
                                        walletExchangeController
                                                ?.setPaymentPassword(paymentPassword)
                                                ?.setType(WalletExchangeController.TYPE_WITHDRAW)
                                                ?.setCurrencySymbol(currencySymbol)
                                                ?.setAmount(
                                                        CurrencyController.getRawValue(
                                                                java.lang.Double.valueOf(et_currency_amount?.text?.toString())
                                                        )
                                                )
                                                ?.makeRequest()
                                    }

                                    override fun onPaymentPasswordCancel() {

                                    }
                                }
                        )
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
                ONGOING,
                WalletWithdrawOngoingFragment::class.java.name,
                WalletWithdrawOngoingFragment.makeArguments(),
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
                WalletWithdrawErrorFragment::class.java.name,
                WalletWithdrawErrorFragment.makeArguments(
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
            ONGOING -> {
                if (resultCode == WalletWithdrawOngoingFragment.RETURN) {
                    activity?.onBackPressed()
                }
            }
            ERROR -> {
                if (resultCode == WalletWithdrawErrorFragment.TRY_AGAIN) {
                    btn_withdraw?.callOnClick()
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
        private val ONGOING = 500
        private val ERROR = 1000

        fun makeArguments(
                currencySymbol: String
        ): Bundle {
            val args = Bundle()

            args.putString(
                    CURRENCY_SYMBOL,
                    currencySymbol
            )

            return args
        }
    }

}
