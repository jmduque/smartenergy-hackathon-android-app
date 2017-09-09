package com.energolabs.evergo.modules.main.requests.requests

import android.content.Context

import com.energolabs.evergo.modules.main.models.MainModel
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
interface MainRequest {

    @GET("users/main")
    fun main(): Call<MainModel>

    class Builder(context: Context) : RequestBuilder<MainModel>(context) {
        override fun request() {
            val mainRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    MainRequest::class.java
            )
            enqueue(
                    mainRequest.main()
            )
        }
    }

}
