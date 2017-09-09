package com.energolabs.evergo.modules.currencyWallet.requests.controllers

import android.content.Context

import com.energolabs.evergo.controllers.BaseRequestController
import com.energolabs.evergo.modules.currencyWallet.models.CurrencyWalletModel
import com.energolabs.evergo.modules.currencyWallet.requests.WalletExchangeRequest
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.utils.CryptoUtils

/**
 * Created by Jose Duque on 12/9/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class WalletExchangeController(
        context: Context,
        resultListener: WalletExchangeController.ResultListener
) : BaseRequestController<CurrencyWalletModel>(context, resultListener) {

    private var type: String? = null
    var amount: Long = 0
    var currencySymbol: String? = null
    private var paymentPassword: String? = null

    override fun makeRequest() {
        WalletExchangeRequest.Builder(context)
                .setPaymentPassword(paymentPassword)
                .setType(type)
                .setAmount(amount)
                .setCurrencySymbol(currencySymbol)
                .setRequestListener(this)
                .request()
    }

    fun setType(type: String): WalletExchangeController {
        this.type = type
        return this
    }

    fun setAmount(amount: Long): WalletExchangeController {
        this.amount = amount
        return this
    }

    fun setCurrencySymbol(currencySymbol: String?): WalletExchangeController {
        this.currencySymbol = currencySymbol
        return this
    }

    fun setPaymentPassword(paymentPassword: String): WalletExchangeController {
        this.paymentPassword = CryptoUtils.md5(paymentPassword)
        return this
    }

    interface ResultListener : BaseResultListener<CurrencyWalletModel>

    companion object {
        val TYPE_TOPUP = "topup"
        val TYPE_WITHDRAW = "withdraw"
    }

}
