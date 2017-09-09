package com.energolabs.evergo.modules.energyWallet.requests

import android.content.Context

import com.energolabs.evergo.modules.energyWallet.models.EnergyExchangeModel
import com.energolabs.evergo.modules.energyWallet.models.EnergyWalletModel
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
interface PostEnergyBalanceRequest {

    @POST("energy/balance")
    fun request(
            @Body energyExchangeModel: EnergyExchangeModel
    ): Call<EnergyWalletModel>

    class Builder(context: Context) : RequestBuilderWithBody<EnergyExchangeModel, EnergyWalletModel>(context) {

        override val request = EnergyExchangeModel()

        override fun setRequest(
                request: EnergyExchangeModel?
        ): Builder {
            setType(request?.type)
            setAmount(request?.amount)
            setSymbol(request?.symbol)
            return this
        }

        fun setType(type: String?): Builder {
            request.type = type
            return this
        }

        fun setAmount(amount: Long?): Builder {
            request.amount = amount ?: 0
            return this
        }

        fun setSymbol(symbol: String?): Builder {
            request.symbol = symbol
            return this
        }

        override fun request() {
            val request = EnergoRetrofitCallBuilder.createService(
                    context,
                    PostEnergyBalanceRequest::class.java
            )

            enqueue(
                    request.request(
                            this.request
                    )
            )
        }

    }

}
