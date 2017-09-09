package com.energolabs.evergo.requests

/**
 * Created by Jose Duque on 12/19/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface BaseResultListener<in Response> {

    fun onResultSuccess(
            tag: Any?,
            response: Response?
    )

    fun onResultError(
            tag: Any?,
            error: String?,
            errorCode: Int
    )

}
