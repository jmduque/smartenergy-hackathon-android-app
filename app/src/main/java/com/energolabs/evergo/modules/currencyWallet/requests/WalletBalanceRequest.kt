package com.energolabs.evergo.modules.currencyWallet.requests

import android.content.Context

import com.energolabs.evergo.modules.currencyWallet.models.CurrencyWalletModel
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilder

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface WalletBalanceRequest {

    @GET("wallet/balance")
    fun balance(): Call<CurrencyWalletModel>

    class Builder(context: Context) : RequestBuilder<CurrencyWalletModel>(context) {

        override fun request() {
            val request = EnergoRetrofitCallBuilder.createService(
                    context,
                    WalletBalanceRequest::class.java
            )

            val call = request.balance()
            enqueue(call)
        }
    }

}
