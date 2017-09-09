package com.energolabs.evergo.modules.battery.requests

import android.content.Context
import com.energolabs.evergo.modules.battery.models.BatteryModel
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
interface GetBatteriesRequest {

    @GET("battery")
    fun request(
            @Query("owner") owner: String?
    ): Call<List<BatteryModel>>

    class Builder(context: Context) : RequestBuilder<List<BatteryModel>>(context) {

        private var owner: String? = null

        fun setOwner(owner: String?): Builder {
            this.owner = owner
            return this
        }

        override fun request() {
            val request = EnergoRetrofitCallBuilder.createService(
                    context,
                    GetBatteriesRequest::class.java
            )

            enqueue(
                    request.request(
                            owner
                    )
            )
        }
    }

}