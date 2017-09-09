package com.energolabs.evergo.modules.user.profile.requests

import android.content.Context

import com.energolabs.evergo.modules.user.profile.models.UserModel
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilder

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Jose on 11/19/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface GetUserRequest {

    @GET("users/{id}")
    fun getUser(
            @Path("id") id: String?
    ): Call<UserModel>

    class Builder(context: Context) : RequestBuilder<UserModel>(context) {

        private var userId: String? = null

        fun setUserId(userId: String?): Builder {
            this.userId = userId
            return this
        }

        override fun request() {
            val getUserRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    GetUserRequest::class.java
            )

            val call = getUserRequest.getUser(
                    userId
            )

            enqueue(call)
        }
    }

    interface ResultListener : BaseResultListener<UserModel>

}
