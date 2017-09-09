package com.energolabs.evergo.utils

import android.text.TextUtils
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

object CryptoUtils {

    fun md5(input: String?): String? {
        if (TextUtils.isEmpty(input)) {
            return null
        }

        input ?: return null

        try {
            val md = MessageDigest.getInstance(
                    "MD5"
            )
            val messageDigest = md.digest(
                    input.toByteArray()
            )
            val number = BigInteger(
                    1,
                    messageDigest
            )
            var md5 = number.toString(16)

            while (md5.length < 32) {
                md5 = "0" + md5
            }

            return md5
        } catch (ignored: Exception) {
            return null
        }
    }

}
