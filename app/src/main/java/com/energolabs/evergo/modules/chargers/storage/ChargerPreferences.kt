package com.energolabs.evergo.modules.chargers.storage

import android.content.Context
import android.content.SharedPreferences
import com.energolabs.evergo.modules.location.models.LocationModel
import com.energolabs.evergo.modules.user.profile.models.UserModel
import com.energolabs.evergo.modules.user.profile.utils.GenderUtils
import com.energolabs.evergo.modules.user.validation.models.IdentityModel

/**
 * Created by Jose Duque on 12/2/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class ChargerPreferences(
        context: Context,
        userId: String?
) {

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(
                PACKAGE_NAME + userId,
                Context.MODE_PRIVATE
        )
    }

    fun cleanPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    fun saveStatus(status: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(
                STATUS,
                status
        )
        editor.apply()
    }

    val status: String
        get() = sharedPreferences.getString(
                STATUS,
                "available"
        )

    companion object {

        private val PACKAGE_NAME = "com.energolabs.evergo.modules.charger_"

        private val STATUS = "status"
    }

}