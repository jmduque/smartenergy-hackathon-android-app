package com.energolabs.evergo.modules.currencyWallet.requests.controllers

import android.content.Context

import com.energolabs.evergo.controllers.BaseRequestController
import com.energolabs.evergo.modules.currencyWallet.models.CurrencyWalletModel
import com.energolabs.evergo.modules.currencyWallet.requests.WalletBalanceRequest
import com.energolabs.evergo.requests.BaseResultListener

/**
 * Created by Jose Duque on 12/9/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class WalletBalanceController(
        context: Context,
        resultListener: WalletBalanceController.ResultListener
) : BaseRequestController<CurrencyWalletModel>(context, resultListener) {

    override fun makeRequest() {
        WalletBalanceRequest.Builder(context)
                .setRequestListener(this)
                .request()
    }

    interface ResultListener : BaseResultListener<CurrencyWalletModel>

}
