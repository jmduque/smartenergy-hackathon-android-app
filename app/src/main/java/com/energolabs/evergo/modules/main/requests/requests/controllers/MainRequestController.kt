package com.energolabs.evergo.modules.main.requests.requests.controllers

import android.content.Context

import com.energolabs.evergo.controllers.BaseRequestController
import com.energolabs.evergo.modules.main.models.MainModel
import com.energolabs.evergo.modules.main.requests.requests.MainRequest
import com.energolabs.evergo.requests.BaseResultListener

/**
 * Created by Jose Duque on 12/9/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class MainRequestController(
        context: Context,
        resultListener: MainRequestController.MainListener
) : BaseRequestController<MainModel>(context, resultListener) {

    override fun makeRequest() {
        MainRequest
                .Builder(context)
                .setRequestListener(this)
                .request()
    }

    interface MainListener : BaseResultListener<MainModel>

}
