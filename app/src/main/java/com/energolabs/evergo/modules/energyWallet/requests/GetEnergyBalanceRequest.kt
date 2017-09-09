package com.energolabs.evergo.modules.energyWallet.requests

import android.content.Context

import com.energolabs.evergo.modules.energyWallet.models.EnergyWalletModel
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
interface GetEnergyBalanceRequest {

    @GET("energy/balance")
    fun request(): Call<EnergyWalletModel>

    class Builder(context: Context) : RequestBuilder<EnergyWalletModel>(context) {

        override fun request() {
            val request = EnergoRetrofitCallBuilder.createService(
                    context,
                    GetEnergyBalanceRequest::class.java
            )

            enqueue(
                    request.request()
            )
        }
    }

}
