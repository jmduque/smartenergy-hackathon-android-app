package com.energolabs.evergo.modules.auth.requests

import android.content.Context

import com.energolabs.evergo.modules.auth.models.PhoneCodeRequest
import com.energolabs.evergo.modules.auth.models.RequestPhoneCodeResponse
import com.energolabs.evergo.requests.BaseResultListener
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
interface RequestPhoneCodeRequest {

    @POST("auth/codes")
    fun refresh(@Body phoneCodeRequest: PhoneCodeRequest): Call<RequestPhoneCodeResponse>

    class Builder(context: Context) : RequestBuilderWithBody<PhoneCodeRequest, RequestPhoneCodeResponse>(context) {

        override val request = PhoneCodeRequest()

        override fun setRequest(
                request: PhoneCodeRequest?
        ): Builder {
            setPhoneNumber(request?.phoneNumber)
            setType(request?.type)
            return this
        }

        fun setPhoneNumber(
                phoneNumber: String?
        ): Builder {
            request.phoneNumber = phoneNumber
            return this
        }

        fun setType(
                type: String?
        ): Builder {
            request.type = type
            return this
        }

        override fun request() {
            val authRefreshRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    RequestPhoneCodeRequest::class.java
            )
            enqueue(
                    authRefreshRequest.refresh(
                            request
                    )
            )
        }
    }

    interface ResultListener : BaseResultListener<RequestPhoneCodeResponse>

}
