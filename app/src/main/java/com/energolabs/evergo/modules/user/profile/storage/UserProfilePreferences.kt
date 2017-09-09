package com.energolabs.evergo.modules.user.profile.storage

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
class UserProfilePreferences(
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

    fun saveName(
            name: String?
    ) {
        val editor = sharedPreferences.edit()
        editor.putString(
                NAME,
                name
        )
        editor.apply()
    }

    val name: String
        get() = sharedPreferences.getString(
                NAME,
                ""
        )

    fun saveAvatar(avatar: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(
                AVATAR,
                avatar
        )
        editor.apply()
    }

    val avatar: String
        get() = sharedPreferences.getString(
                AVATAR,
                ""
        )

    fun saveAddress(address: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(
                ADDRESS,
                address
        )
        editor.apply()
    }

    val address: String
        get() = sharedPreferences.getString(
                ADDRESS,
                ""
        )

    fun saveIdentity(identityModel: IdentityModel?) {
        val editor = sharedPreferences.edit()
        editor.putString(
                IDENTITY,
                identityModel?.toString()
        )
        editor.apply()
    }

    val identity: IdentityModel
        get() = IdentityModel.fromString(
                sharedPreferences.getString(
                        IDENTITY,
                        "{}"
                )
        )

    fun saveLocation(locationModel: LocationModel?) {
        val editor = sharedPreferences.edit()
        editor.putString(
                LOCATION,
                locationModel?.toString()
        )
        editor.apply()
    }

    val location: LocationModel
        get() = LocationModel.fromString(
                sharedPreferences.getString(
                        LOCATION,
                        "{}"
                )
        )

    fun saveGender(gender: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(
                GENDER,
                gender
        )
        editor.apply()
    }

    val gender: Int
        get() = sharedPreferences.getInt(
                GENDER,
                0
        )

    fun saveHasPaymentPassword(hasPaymentPassword: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(
                HAS_PAYMENT_PASSWORD,
                hasPaymentPassword
        )
        editor.apply()
    }

    val hasPaymentPassword: Boolean
        get() = sharedPreferences.getBoolean(
                HAS_PAYMENT_PASSWORD,
                false
        )

    fun saveUserModel(model: UserModel?) {
        saveName(model?.name)
        saveAvatar(model?.avatar)
        saveGender(model?.gender ?: GenderUtils.UNKNOWN)
        saveAddress(model?.address)
        saveIdentity(model?.identity)
        saveLocation(model?.location)
        saveHasPaymentPassword(model?.hasPaymentPassword ?: false)
    }

    val userModel: UserModel
        get() {
            val model = UserModel()
            model.name = name
            model.avatar = avatar
            model.gender = gender
            model.address = address
            model.identity = identity
            model.location = location
            model.hasPaymentPassword = hasPaymentPassword
            return model
        }

    companion object {

        private val PACKAGE_NAME = "com.energolabs.evergo.modules.user.profile_"

        private val NAME = "name"
        private val AVATAR = "avatar"
        private val GENDER = "gender"
        private val ADDRESS = "address"
        private val IDENTITY = "identity"
        private val LOCATION = "location"
        private val HAS_PAYMENT_PASSWORD = "has_payment_password"
    }

}
