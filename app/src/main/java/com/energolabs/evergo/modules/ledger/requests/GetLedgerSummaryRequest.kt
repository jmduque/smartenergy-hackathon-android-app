package com.energolabs.evergo.modules.ledger.requests

import android.content.Context

import com.energolabs.evergo.modules.ledger.models.LedgerSummaryModel
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
interface GetLedgerSummaryRequest {

    @GET("ledger/summary")
    fun request(
            @Query("type") type: String?,
            @Query("offset") offset: Int
    ): Call<List<LedgerSummaryModel>>

    class Builder(context: Context) : RequestBuilder<List<LedgerSummaryModel>>(context) {

        private var type: String? = null
        private var offset = 0

        fun setType(type: String): Builder {
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
                    GetLedgerSummaryRequest::class.java
            )

            enqueue(
                    request.request(
                            type,
                            offset
                    )
            )
        }
    }

    interface ResultListener : BaseResultListener<List<LedgerSummaryModel>>

}
