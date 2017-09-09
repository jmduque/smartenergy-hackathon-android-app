package com.energolabs.evergo.requests

import android.content.Context
import com.energolabs.evergo.utils.json.getString
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Jose Duque on 12/6/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

abstract class RequestBuilder<ResponseModel>(
        protected val context: Context
) :
        Callback<ResponseModel>,
        RequestListener<ResponseModel> {
    private var requestListener: RequestListener<ResponseModel>? = null
    private var baseResultListener: BaseResultListener<ResponseModel>? = null
    private var tag: Any? = null

    init {
        requestListener = this
    }

    abstract fun request()

    fun setRequestListener(
            requestListener: RequestListener<ResponseModel>
    ): RequestBuilder<ResponseModel> {
        this.requestListener = requestListener
        return this
    }

    fun setTag(tag: Any?): RequestBuilder<ResponseModel> {
        this.tag = tag
        return this
    }

    fun setResultListener(baseResultListener: BaseResultListener<ResponseModel>?): RequestBuilder<ResponseModel> {
        this.baseResultListener = baseResultListener
        return this
    }

    protected fun enqueue(call: Call<ResponseModel>) {
        try {
            call.enqueue(this)
        } catch (ignored: Exception) {
            requestListener?.onError(
                    call,
                    null,
                    throw Exception("Unable to add request to Queue")
            )
        }

    }

    override fun onResponse(
            call: Call<ResponseModel>,
            response: retrofit2.Response<ResponseModel>?
    ) {
        if (response?.isSuccessful ?: false) {
            requestListener?.onSuccess(
                    call,
                    response
            )
        } else {
            requestListener?.onError(
                    call,
                    response,
                    Exception("Strange Error")
            )
        }
    }

    override fun onFailure(
            call: Call<ResponseModel>,
            t: Throwable
    ) {
        requestListener?.onError(
                call,
                null,
                t
        )
    }

    override fun onError(
            call: Call<ResponseModel>,
            response: Response<ResponseModel>?,
            t: Throwable
    ) {
        if (baseResultListener == null) {
            return
        }
        var errorObject: JSONObject? = null
        try {
            errorObject = JSONObject(
                    response?.errorBody()?.string()
            )
        } catch (ignored: Exception) {
        }

        baseResultListener?.onResultError(
                tag,
                getString(
                        errorObject,
                        "error"
                ),
                response?.code() ?: 0
        )
    }

    override fun onSuccess(
            call: Call<ResponseModel>,
            response: Response<ResponseModel>?
    ) {
        baseResultListener?.onResultSuccess(
                tag,
                response?.body()
        )
    }

}
