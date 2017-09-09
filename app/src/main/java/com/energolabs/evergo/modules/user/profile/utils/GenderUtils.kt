package com.energolabs.evergo.modules.user.profile.utils

import android.content.Context
import android.support.annotation.DrawableRes

import com.energolabs.evergo.R

/**
 * Created by Jose Duque on 1/13/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

object GenderUtils {

    val UNKNOWN = 0
    val MALE = 1
    val FEMALE = 2
    val OTHER = 9

    fun getGenderText(
            context: Context,
            gender: Int?
    ): String {
        when (gender) {
            MALE -> {
                return context.getString(R.string.energo_user_profile_gender_male)
            }
            FEMALE -> {
                return context.getString(R.string.energo_user_profile_gender_female)
            }
            OTHER -> {
                return context.getString(R.string.energo_user_profile_gender_other)
            }
            else -> {
                return context.getString(R.string.energo_user_profile_gender_unknown)
            }
        }
    }

    @DrawableRes
    fun getDefaultAvatarResource(
            gender: Int?
    ): Int {
        when (gender) {
            UNKNOWN -> {
                return R.drawable.ic_default_avatar_boy
            }
            MALE -> {
                return R.drawable.ic_default_avatar_boy
            }
            FEMALE -> {
                return R.drawable.ic_default_avatar_girl
            }
            OTHER -> {
                return R.drawable.ic_default_avatar_boy
            }
            else -> {
                return R.drawable.ic_default_avatar_boy
            }
        }
    }

}
