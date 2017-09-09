package com.energolabs.evergo.modules.currencyWallet.requests.controllers

import android.content.Context

import com.energolabs.evergo.controllers.BaseRequestController
import com.energolabs.evergo.modules.currencyWallet.models.WalletTransactionModel
import com.energolabs.evergo.modules.currencyWallet.requests.WalletTransactionsRequest
import com.energolabs.evergo.requests.BaseResultListener

/**
 * Created by Jose Duque on 12/9/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class WalletTransactionsController(
        context: Context,
        resultListener: WalletTransactionsController.ResultListener
) : BaseRequestController<List<WalletTransactionModel>>(context, resultListener) {

    private var count = 0

    override fun makeRequest() {
        WalletTransactionsRequest.Builder(context)
                .setOffset(count)
                .setRequestListener(this)
                .request()
    }

    fun setCount(count: Int) {
        this.count = count
    }

    interface ResultListener : BaseResultListener<List<WalletTransactionModel>>

}
