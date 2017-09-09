package com.energolabs.evergo.utils

import android.text.TextUtils

import com.energolabs.evergo.BuildConfig

/**
 * Created by Jose on 7/28/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
object BuildUtils {

    /**
     * Invokes [BuildUtils.isMarketBuild] using
     * [BuildConfig.FLAVOR] as flavorName
     */
    val isMarketBuild: Boolean
        get() = isMarketBuild(BuildConfig.FLAVOR)

    /**
     * This methods reports if the current application build flavor is
     * one of the application flavors expected to be used to release
     * to markets such as "Play Store", "Xiaomi Store", etc.

     * @param flavorName The reference flavor's name
     * *
     * @return true if version flavor is neither "_test", "web" or "staging"
     */
    fun isMarketBuild(flavorName: String): Boolean {
        if (TextUtils.isEmpty(flavorName)) {
            return false
        }

        when (flavorName) {
            "web" -> return false
            "staging" -> return false
            "_test" -> return false
            else -> return true
        }
    }

}
