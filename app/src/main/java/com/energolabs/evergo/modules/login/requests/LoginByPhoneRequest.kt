package com.energolabs.evergo.modules.login.requests

import android.content.Context

import com.energolabs.evergo.modules.auth.models.AuthModel
import com.energolabs.evergo.modules.login.models.LoginByPhoneModel
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilderWithBody
import com.energolabs.evergo.utils.CryptoUtils

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface LoginByPhoneRequest {

    @POST("auth/login")
    fun login(@Body loginByPhoneModel: LoginByPhoneModel): Call<AuthModel>

    class Builder(context: Context) : RequestBuilderWithBody<LoginByPhoneModel, AuthModel>(context) {

        override val request = LoginByPhoneModel()
        private var loginRequestListener: LoginRequestListener? = null

        fun setLoginRequestListener(loginRequestListener: LoginRequestListener): Builder {
            this.loginRequestListener = loginRequestListener
            return this
        }

        override fun setRequest(request: LoginByPhoneModel?): Builder {
            this.request.phoneNumber = request?.phoneNumber
            this.request.password = request?.password
            return this
        }

        fun setPhoneNumber(phoneNumber: String?): Builder {
            request.phoneNumber = phoneNumber
            return this
        }

        /**
         * @param password Plain password
         * *
         */
        fun setPassword(password: String?): Builder {
            request.password = CryptoUtils.md5(password)
            return this
        }

        override fun request() {
            val loginByPhoneRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    LoginByPhoneRequest::class.java
            )

            val call = loginByPhoneRequest.login(
                    request
            )

            try {
                call.enqueue(this)
            } catch (ignored: Exception) {
                loginRequestListener?.onLoginByPhoneError(
                        call, null, null
                )
            }

        }

        override fun onResponse(
                call: Call<AuthModel>,
                response: Response<AuthModel>?
        ) {
            if (response?.isSuccessful ?: false) {
                loginRequestListener?.onLoginByPhoneSuccess(
                        call,
                        response
                )
            } else {
                loginRequestListener?.onLoginByPhoneError(
                        call,
                        response,
                        null
                )
            }
        }

        override fun onFailure(
                call: Call<AuthModel>,
                t: Throwable
        ) {
            loginRequestListener?.onLoginByPhoneError(
                    call,
                    null,
                    t
            )
        }
    }

    interface LoginRequestListener {

        fun onLoginByPhoneError(
                call: Call<AuthModel>,
                response: Response<AuthModel>?,
                t: Throwable?
        )

        fun onLoginByPhoneSuccess(
                call: Call<AuthModel>,
                response: Response<AuthModel>?
        )

    }

}
