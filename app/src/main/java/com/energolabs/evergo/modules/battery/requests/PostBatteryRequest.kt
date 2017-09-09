package com.energolabs.evergo.modules.battery.requests

import android.content.Context
import com.energolabs.evergo.modules.battery.models.BatteryModel
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
interface PostBatteryRequest {

    @POST("battery")
    fun request(@Body batteryModel: BatteryModel): Call<BatteryModel>

    class Builder(context: Context) : RequestBuilderWithBody<
            BatteryModel,
            BatteryModel
            >(context) {

        override val request = BatteryModel()

        override fun setRequest(request: BatteryModel?): Builder {
            this.request.name = request?.name
            this.request.capacity = request?.capacity
            this.request.type = request?.type
            this.request.location = request?.location
            return this
        }

        override fun request() {
            val petUserRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    PostBatteryRequest::class.java
            )

            val call = petUserRequest.request(
                    request
            )
            enqueue(call)
        }
    }

}