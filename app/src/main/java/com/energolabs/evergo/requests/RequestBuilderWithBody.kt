package com.energolabs.evergo.requests

import android.content.Context

/**
 * Created by Jose Duque on 12/6/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

abstract class RequestBuilderWithBody<Request, Response>(
        context: Context
) : RequestBuilder<Response>(context) {

    abstract val request: Request

    abstract fun setRequest(request: Request?): RequestBuilderWithBody<Request, Response>

}
