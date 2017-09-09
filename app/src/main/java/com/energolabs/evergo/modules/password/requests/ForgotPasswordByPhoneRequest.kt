package com.energolabs.evergo.modules.password.requests

import android.content.Context

import com.energolabs.evergo.modules.auth.models.AuthModel
import com.energolabs.evergo.modules.password.models.ForgotPasswordByPhoneModel
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
interface ForgotPasswordByPhoneRequest {

    @POST("auth/password/forgot")
    fun submit(@Body body: ForgotPasswordByPhoneModel): Call<AuthModel>

    class Builder(context: Context) : RequestBuilderWithBody<ForgotPasswordByPhoneModel, AuthModel>(context) {

        override val request = ForgotPasswordByPhoneModel()

        override fun setRequest(request: ForgotPasswordByPhoneModel?): Builder {
            setPhoneNumber(
                    request?.phoneNumber
            )
            setPhoneCode(
                    request?.phoneCode
            )
            setNewPassword(
                    request?.newPassword
            )
            return this
        }

        fun setPhoneNumber(phoneNumber: String?): Builder {
            request.phoneNumber = phoneNumber
            return this
        }

        fun setPhoneCode(phoneCode: String?): Builder {
            request.phoneCode = phoneCode
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
            val forgotPasswordByPhoneRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    ForgotPasswordByPhoneRequest::class.java
            )

            val call = forgotPasswordByPhoneRequest.submit(
                    request
            )

            enqueue(
                    call
            )
        }

    }

    interface ForgotPasswordByPhoneResultListener : BaseResultListener<AuthModel>

}
