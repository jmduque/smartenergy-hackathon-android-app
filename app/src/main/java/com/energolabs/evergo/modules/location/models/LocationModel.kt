package com.energolabs.evergo.modules.location.models

import com.energolabs.evergo.models.BaseModel
import com.google.gson.Gson

/**
 * Created by Jose Duque on 2/9/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class LocationModel : BaseModel() {

    var areaCode: Int = 0
    var areaName: String? = null

    override fun toString(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromString(location: String): LocationModel {
            return Gson().fromJson(location, LocationModel::class.java)
        }
    }

}
