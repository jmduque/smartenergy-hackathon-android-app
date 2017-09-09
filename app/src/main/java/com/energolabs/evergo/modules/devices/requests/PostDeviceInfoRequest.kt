package com.energolabs.evergo.modules.devices.requests

import android.content.Context

import com.energolabs.evergo.modules.devices.models.DeviceInfo
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilderWithBody

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Jose on 11/19/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface PostDeviceInfoRequest {

    @POST("devices")
    fun bindDevice(
            @Body deviceInfo: DeviceInfo
    ): Call<DeviceInfo>

    class Builder(context: Context) : RequestBuilderWithBody<DeviceInfo, DeviceInfo>(context) {

        override val request = DeviceInfo()

        override fun setRequest(request: DeviceInfo?): Builder {

            this.request._id = request?._id
            this.request.name = request?.name
            this.request.model = request?.model
            this.request.hwVersion = request?.hwVersion
            this.request.address = request?.address
            this.request.location = request?.location
            this.request.uuid = request?.uuid
            this.request.type = request?.type
            return this
        }

        override fun request() {
            val petUserRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    PostDeviceInfoRequest::class.java
            )

            val call = petUserRequest.bindDevice(
                    request
            )
            enqueue(call)
        }
    }

}
