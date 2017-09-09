package com.energolabs.evergo.modules.register.requests

import android.content.Context

import com.energolabs.evergo.modules.auth.models.AuthModel
import com.energolabs.evergo.modules.register.models.RegisterByPhoneModel
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
interface RegisterByPhoneRequest {

    @POST("auth/register")
    fun register(@Body body: RegisterByPhoneModel): Call<AuthModel>

    class Builder(context: Context) : RequestBuilderWithBody<RegisterByPhoneModel, AuthModel>(context) {

        override val request = RegisterByPhoneModel()

        override fun setRequest(request: RegisterByPhoneModel?): Builder {
            setPhoneNumber(
                    request?.phoneNumber
            )
            setPhoneCode(
                    request?.phoneCode
            )
            setPassword(
                    request?.password
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
        fun setPassword(password: String?): Builder {
            request.password = CryptoUtils.md5(password)
            return this
        }

        override fun request() {
            val registerByPhoneRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    RegisterByPhoneRequest::class.java
            )

            val call = registerByPhoneRequest.register(
                    request
            )

            enqueue(
                    call
            )
        }

    }

    interface RegisterByPhoneResultListener : BaseResultListener<AuthModel>

}
