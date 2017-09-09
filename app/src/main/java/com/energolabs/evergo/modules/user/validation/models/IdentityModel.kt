package com.energolabs.evergo.modules.user.validation.models

import com.energolabs.evergo.models.BaseModel
import com.google.gson.Gson

/**
 * Created by Jose Duque on 2/7/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class IdentityModel : BaseModel() {

    var verified: Boolean = false
    var accountType: String? = null
    var photos: List<IdentityPhoto>? = null

    override fun toString(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromString(identity: String): IdentityModel {
            return Gson().fromJson(identity, IdentityModel::class.java)
        }
    }

}
