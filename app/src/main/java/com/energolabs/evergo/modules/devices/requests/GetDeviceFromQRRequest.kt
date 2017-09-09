package com.energolabs.evergo.modules.devices.requests

import android.content.Context

import com.energolabs.evergo.modules.devices.models.DeviceInfo
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilder

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Jose Duque on 02/07/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface GetDeviceFromQRRequest {

    @GET
    fun request(
            @Url url: String
    ): Call<DeviceInfo>

    class Builder(context: Context) : RequestBuilder<DeviceInfo>(context) {

        private var url: String = ""

        fun setUrl(url: String): Builder {
            this.url = url
            return this
        }

        override fun request() {
            val request = EnergoRetrofitCallBuilder.createService(
                    context,
                    GetDeviceFromQRRequest::class.java
            )

            enqueue(
                    request.request(
                            url
                    )
            )
        }
    }

}
