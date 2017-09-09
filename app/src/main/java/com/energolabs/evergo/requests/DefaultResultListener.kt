package com.energolabs.evergo.requests


import android.content.Context
import android.widget.Toast

/**
 * Created by Jose Duque on 4/19/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

abstract class DefaultResultListener<in Result>(
        private val context: Context?
) : BaseResultListener<Result> {

    override fun onResultError(
            tag: Any?,
            error: String?,
            errorCode: Int
    ) {
        context ?: return
        Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
        ).show()
    }

}
