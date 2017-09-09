package com.energolabs.evergo.requests

/**
 * Created by Jose Duque on 12/19/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

import retrofit2.Call
import retrofit2.Response

interface RequestListener<ResponseModel> {

    fun onError(
            call: Call<ResponseModel>,
            response: Response<ResponseModel>?,
            t: Throwable
    )

    fun onSuccess(
            call: Call<ResponseModel>,
            response: Response<ResponseModel>?
    )

}