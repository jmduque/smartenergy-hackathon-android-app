package com.energolabs.evergo.modules.auth.requests

import android.content.Context

import com.energolabs.evergo.modules.auth.models.AuthModel
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
interface AuthRefreshRequest {

    @POST("auth/refresh")
    fun refresh(@Body authModel: AuthModel): Call<AuthModel>

    class Builder(context: Context) : RequestBuilderWithBody<AuthModel, AuthModel>(context) {

        override val request: AuthModel = AuthModel()

        override fun setRequest(request: AuthModel?): Builder {
            this.request.userId = request?.userId
            this.request.refreshToken = request?.refreshToken
            return this
        }

        fun setUserId(userId: String): Builder {
            request.userId = userId
            return this
        }

        fun setRefreshToken(refreshToken: String): Builder {
            request.refreshToken = refreshToken
            return this
        }

        override fun request() {
            val authRefreshRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    AuthRefreshRequest::class.java
            )
            enqueue(
                    authRefreshRequest.refresh(
                            request
                    )
            )
        }
    }

    interface ResultListener : BaseResultListener<AuthModel>

}