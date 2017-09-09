package com.energolabs.evergo.modules.devices.requests

import android.content.Context
import com.energolabs.evergo.modules.devices.models.DeviceInfo
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilder
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jose Duque on 02/07/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface GetDevicesRequest {

    @GET("devices")
    fun request(
            @Query("type") type: String?,
            @Query("offset") offset: Int
    ): Call<List<DeviceInfo>>

    class Builder(context: Context) : RequestBuilder<List<DeviceInfo>>(context) {

        private var type: String? = null
        private var offset: Int = 0

        fun setType(type: String?): Builder {
            this.type = type
            return this
        }

        fun setOffset(offset: Int): Builder {
            this.offset = offset
            return this
        }

        override fun request() {
            val request = EnergoRetrofitCallBuilder.createService(
                    context,
                    GetDevicesRequest::class.java
            )

            enqueue(
                    request.request(
                            type,
                            offset
                    )
            )
        }
    }

}
