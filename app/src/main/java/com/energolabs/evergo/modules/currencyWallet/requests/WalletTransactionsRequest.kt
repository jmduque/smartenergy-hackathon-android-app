package com.energolabs.evergo.modules.currencyWallet.requests

import android.content.Context

import com.energolabs.evergo.modules.currencyWallet.models.WalletTransactionModel
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilder

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface WalletTransactionsRequest {

    @GET("wallet/transactions")
    fun balance(
            @Query("offset") offset: Int?
    ): Call<List<WalletTransactionModel>>

    class Builder(context: Context) : RequestBuilder<List<WalletTransactionModel>>(context) {

        private var offset = 0

        fun setOffset(offset: Int?): Builder {
            this.offset = offset ?: 0
            return this
        }

        override fun request() {
            val request = EnergoRetrofitCallBuilder.createService(
                    context,
                    WalletTransactionsRequest::class.java
            )

            val call = request.balance(offset)
            enqueue(call)
        }
    }

}
