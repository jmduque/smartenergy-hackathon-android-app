package com.energolabs.evergo.controllers

import android.content.Context
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.requests.RequestListener
import com.energolabs.evergo.utils.json.getString
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Jose Duque on 12/9/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

abstract class BaseRequestController<ResponseModel>(
        protected var context: Context,
        protected var baseResultListener: BaseResultListener<ResponseModel>?
) : RequestListener<ResponseModel> {

    protected var objectTag: Any? = null

    abstract fun makeRequest()

    fun setTag(tag: Any?): BaseRequestController<ResponseModel> {
        this.objectTag = tag
        return this
    }

    override fun onError(
            call: Call<ResponseModel>,
            response: Response<ResponseModel>?,
            t: Throwable
    ) {
        baseResultListener ?: return
        var errorObject: JSONObject? = null
        try {
            errorObject = JSONObject(
                    response?.errorBody()?.string()
            )
        } catch (ignored: Exception) {
        }

        baseResultListener?.onResultError(
                objectTag,
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
                objectTag,
                response?.body()
        )
    }

}
