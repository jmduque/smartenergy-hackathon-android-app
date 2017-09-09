package com.energolabs.evergo.modules.devices.requests

import android.content.Context

import com.energolabs.evergo.modules.devices.models.DeviceInfo
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilderWithBody

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Created by Jose on 11/19/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface PutDeviceInfoRequest {

    @PUT("devices/{id}")
    fun putDevice(
            @Body deviceInfo: DeviceInfo,
            @Path("id") id: String?
    ): Call<DeviceInfo>

    class Builder(context: Context) : RequestBuilderWithBody<DeviceInfo, DeviceInfo>(context) {

        override val request = DeviceInfo()

        override fun setRequest(request: DeviceInfo?): Builder {
            this.request._id = request?._id
            this.request.name = request?.name
            return this
        }

        fun setId(id: String?): Builder {
            request._id = id
            return this
        }

        fun setName(name: String?): Builder {
            request.name = name
            return this
        }

        override fun request() {
            val petUserRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    PutDeviceInfoRequest::class.java
            )

            val call = petUserRequest.putDevice(
                    request,
                    request._id
            )
            enqueue(call)
        }
    }

    interface PutDeviceInfoRequestListener : BaseResultListener<DeviceInfo>

}
