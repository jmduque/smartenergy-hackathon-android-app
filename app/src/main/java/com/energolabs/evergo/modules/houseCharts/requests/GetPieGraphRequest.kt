package com.energolabs.evergo.modules.houseCharts.requests

import android.content.Context

import com.energolabs.evergo.modules.houseCharts.models.GetPieGraphResponse
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilder

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface GetPieGraphRequest {

    @GET("house/piegraph")
    fun request(
            @Query("period") period: String?
    ): Call<GetPieGraphResponse>

    class Builder(context: Context) : RequestBuilder<GetPieGraphResponse>(context) {

        private var period: String? = null

        fun setPeriod(period: String): Builder {
            this.period = period
            return this
        }

        override fun request() {
            val request = EnergoRetrofitCallBuilder.createService(
                    context,
                    GetPieGraphRequest::class.java
            )

            enqueue(
                    request.request(
                            period
                    )
            )
        }
    }

    interface ResultListener : BaseResultListener<GetPieGraphResponse>

    companion object {
        val DAILY = "day"
        val WEEKLY = "week"
        val MONTHLY = "month"
        val YEARLY = "year"
    }

}
