package com.energolabs.evergo.modules.password.requests

import android.content.Context

import com.energolabs.evergo.modules.auth.models.AuthModel
import com.energolabs.evergo.modules.password.models.ChangePasswordModel
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilderWithBody
import com.energolabs.evergo.utils.CryptoUtils

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
interface ChangePasswordRequest {

    @POST("auth/password/change")
    fun changePassword(@Body body: ChangePasswordModel): Call<AuthModel>

    class Builder(
            context: Context
    ) : RequestBuilderWithBody<ChangePasswordModel, AuthModel>(
            context
    ) {

        override val request = ChangePasswordModel()

        override fun setRequest(request: ChangePasswordModel?): Builder {
            setPassword(
                    request?.password
            )
            setNewPassword(
                    request?.newPassword
            )
            return this
        }

        /**
         * @param password Plain password
         */
        fun setPassword(password: String?): Builder {
            request.password = CryptoUtils.md5(password)
            return this
        }

        /**
         * @param password Plain password
         */
        fun setNewPassword(password: String?): Builder {
            request.newPassword = CryptoUtils.md5(password)
            return this
        }

        override fun request() {
            val serviceRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    ChangePasswordRequest::class.java
            )

            val call = serviceRequest.changePassword(
                    request
            )

            enqueue(
                    call
            )
        }

    }

    interface ChangePasswordResultListener : BaseResultListener<AuthModel>

}
