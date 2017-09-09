package com.energolabs.evergo.models

import android.text.TextUtils

import java.io.Serializable

/**
 * Created by Jose on 11/19/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
abstract class BaseModel : Serializable {

    var _id: String? = null
    var createdAt: String? = null
    var updatedAt: String? = null

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other.javaClass != this.javaClass) {
            return false
        }

        if (TextUtils.isEmpty(_id)) {
            return false
        }

        return TextUtils.equals(
                _id,
                (other as BaseModel)._id
        )
    }

    override fun hashCode(): Int{
        var result = _id?.hashCode() ?: 0
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + (updatedAt?.hashCode() ?: 0)
        return result
    }

}
