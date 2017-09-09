package com.energolabs.evergo.modules.energyWallet.requests.controllers

import android.content.Context

import com.energolabs.evergo.controllers.BaseRequestController
import com.energolabs.evergo.modules.energyWallet.models.EnergyWalletModel
import com.energolabs.evergo.modules.energyWallet.requests.GetEnergyBalanceRequest
import com.energolabs.evergo.requests.BaseResultListener

/**
 * Created by Jose Duque on 12/9/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class GetEnergyBalanceController(
        context: Context,
        resultListener: GetEnergyBalanceController.ResultListener
) : BaseRequestController<EnergyWalletModel>(context, resultListener) {

    override fun makeRequest() {
        GetEnergyBalanceRequest.Builder(context)
                .setRequestListener(this)
                .request()
    }

    interface ResultListener : BaseResultListener<EnergyWalletModel>

}
