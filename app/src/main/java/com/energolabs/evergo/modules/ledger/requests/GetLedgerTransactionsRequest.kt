package com.energolabs.evergo.modules.ledger.requests

import android.content.Context

import com.energolabs.evergo.modules.ledger.models.LedgerSummaryModel
import com.energolabs.evergo.modules.ledger.models.TransactionWrapperModel
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
interface GetLedgerTransactionsRequest {

    @GET("ledger/transactions")
    fun request(
            @Query("offset") offset: Int,
            @Query("startDate") startDate: String?,
            @Query("endDate") endDate: String?
    ): Call<List<TransactionWrapperModel>>

    class Builder(context: Context) : RequestBuilder<List<TransactionWrapperModel>>(context) {

        private var offset = 0
        private var startDate: String? = null
        private var endDate: String? = null

        fun setOffset(offset: Int?): Builder {
            this.offset = offset ?: 0
            return this
        }

        fun setStartDate(startDate: String?): Builder {
            this.startDate = startDate
            return this
        }

        fun setEndDate(endDate: String?): Builder {
            this.endDate = endDate
            return this
        }

        override fun request() {
            val request = EnergoRetrofitCallBuilder.createService(
                    context,
                    GetLedgerTransactionsRequest::class.java
            )

            enqueue(
                    request.request(
                            offset,
                            startDate,
                            endDate
                    )
            )
        }

    }

    interface ResultListener : BaseResultListener<List<LedgerSummaryModel>>

}
