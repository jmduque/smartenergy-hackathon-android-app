package com.energolabs.evergo.modules.currencyWallet.requests

import android.content.Context

import com.energolabs.evergo.modules.currencyWallet.models.CurrencyExchangeModel
import com.energolabs.evergo.modules.currencyWallet.models.CurrencyWalletModel
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilderWithBody

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface WalletExchangeRequest {

    @POST("wallet/balance")
    fun request(
            @Body body: CurrencyExchangeModel?
    ): Call<CurrencyWalletModel>

    class Builder(context: Context) : RequestBuilderWithBody<CurrencyExchangeModel, CurrencyWalletModel>(context) {

        override val request = CurrencyExchangeModel()

        override fun setRequest(
                request: CurrencyExchangeModel?
        ): Builder {
            setPaymentPassword(
                    request?.paymentPassword
            )
            setType(
                    request?.type
            )
            setAmount(
                    request?.amount ?: 0
            )
            setCurrencySymbol(
                    request?.symbol
            )
            return this
        }

        fun setPaymentPassword(paymentPassword: String?): Builder {
            request.paymentPassword = paymentPassword
            return this
        }

        fun setType(type: String?): Builder {
            request.type = type
            return this
        }

        fun setAmount(amount: Long): Builder {
            request.amount = amount
            return this
        }

        fun setCurrencySymbol(currencySymbol: String?): Builder {
            request.symbol = currencySymbol
            return this
        }

        override fun request() {
            val call = EnergoRetrofitCallBuilder.createService(
                    context,
                    WalletExchangeRequest::class.java
            )

            enqueue(
                    call.request(
                            request
                    )
            )
        }
    }

}
