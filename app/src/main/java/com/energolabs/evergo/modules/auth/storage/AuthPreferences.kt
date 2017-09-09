package com.energolabs.evergo.modules.auth.storage

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.energolabs.evergo.modules.auth.models.AuthModel

/**
 * Created by Jose Duque on 12/2/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class AuthPreferences(context: Context) {

    private val PACKAGE_NAME = "com.energolabs.evergo.modules.auth"
    private val ACCESS_TOKEN = "access_token"
    private val REFRESH_TOKEN = "refresh_token"
    private val USER_ID = "user_id"
    private val ROLE = "role"

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(
                PACKAGE_NAME,
                Context.MODE_PRIVATE
        )
    }

    fun cleanPreferences() {
        sharedPreferences.edit()?.clear()?.apply()
    }

    fun saveAccessToken(
            accessToken: String?
    ) {
        val editor = sharedPreferences.edit()
        editor?.putString(
                ACCESS_TOKEN,
                accessToken
        )
        editor?.apply()
    }

    val accessToken: String
        get() = sharedPreferences.getString(
                ACCESS_TOKEN,
                ""
        )

    fun saveRefreshToken(refreshToken: String?) {
        val editor = sharedPreferences.edit()
        editor?.putString(
                REFRESH_TOKEN,
                refreshToken
        )
        editor?.apply()
    }

    val refreshToken: String
        get() = sharedPreferences.getString(
                REFRESH_TOKEN,
                ""
        )

    fun saveUserId(userId: String?) {
        val editor = sharedPreferences.edit()
        editor?.putString(
                USER_ID,
                userId
        )
        editor?.apply()
    }

    val userId: String
        get() = sharedPreferences.getString(
                USER_ID,
                ""
        )

    fun saveRole(role: String?) {
        val editor = sharedPreferences.edit()
        editor?.putString(
                ROLE,
                role
        )
        editor?.apply()
    }

    val role: String
        get() = sharedPreferences.getString(
                ROLE,
                ""
        )

    fun saveAuthModel(authModel: AuthModel?) {
        saveUserId(authModel?.userId)
        saveAccessToken(authModel?.accessToken)
        saveRefreshToken(authModel?.refreshToken)
        saveRole(authModel?.role)
    }

    val authModel: AuthModel?
        get() {
            val userId = userId
            if (TextUtils.isEmpty(userId)) {
                return null
            }
            val authModel = AuthModel()
            authModel.userId = userId

            val accessToken = accessToken
            if (TextUtils.isEmpty(accessToken)) {
                return null
            }
            authModel.accessToken = accessToken

            val refreshToken = refreshToken
            if (TextUtils.isEmpty(refreshToken)) {
                return null
            }
            authModel.refreshToken = refreshToken

            authModel.role = role

            return authModel
        }

}
