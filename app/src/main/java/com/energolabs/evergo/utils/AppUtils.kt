package com.energolabs.evergo.utils

import android.text.TextUtils

import com.energolabs.evergo.BuildConfig

/**
 * Created by Jose on 6/3/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
object AppUtils {

    /**
     * This method reports the canonical version number accordingly to the
     * version name without any suffixes and prefixes according to the version name
     * defined at [BuildConfig.VERSION_NAME], i.e., for a version name defined
     * as "2.2.4W-S", the canonical version number would match to "2.2.4".

     * @return canonical version number.
     */
    val appCanonicalVersionName: String?
        get() = getAppCanonicalVersionName(BuildConfig.VERSION_NAME)

    /**
     * This method reports the canonical version number accordingly to the
     * version name without any suffixes and prefixes according to the provided
     * version name. i.e., for a version name defined as "2.2.4W-S", the canonical
     * version number would match to "2.2.4".

     * @param versionName The version name of the application
     * *
     * @return canonical version number accordingly to the provided `versionName`.
     */
    fun getAppCanonicalVersionName(versionName: String): String? {
        if (TextUtils.isEmpty(versionName)) {
            return null
        }

        return versionName.replace("[a-zA-Z,-]".toRegex(), "")
    }

}
