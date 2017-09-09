package com.energolabs.evergo.modules.location.requests

import android.content.Context

import com.energolabs.evergo.modules.location.models.LocationModel
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilder

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jose Duque on 2/9/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

interface GetLocationsRequest {

    @GET("location")
    fun request(
            @Query("area") area: Int?,
            @Query("offset") offset: Int
    ): Call<List<LocationModel>>

    class Builder(context: Context) : RequestBuilder<List<LocationModel>>(context) {

        private var area: Int? = null
        private var offset: Int = 0

        fun setArea(area: Int?): Builder {
            this.area = area
            return this
        }

        fun setOffset(offset: Int): Builder {
            this.offset = offset
            return this
        }

        override fun request() {
            val request = EnergoRetrofitCallBuilder.createService(
                    context,
                    GetLocationsRequest::class.java
            )

            enqueue(
                    request.request(
                            if ((area?.compareTo(0) ?: 0) <= 0) null else area,
                            offset
                    )
            )
        }
    }

}
