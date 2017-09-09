package com.energolabs.evergo.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jose Duque on 12/13/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

object DateUtils {

    private val iso8601 = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.ENGLISH
    )

    /**
     * Parses a provided **ISO8601** formatted date
     * (2015-03-27T21:07:46+07:00) and returns a java **Date** object

     * @param inputDate ISO8601 formatted date
     * *
     * @return if **inputDate** is a valid **ISO8601** formatted String, it
     * * will return the expected **Date** object, otherwise it will
     * * return a **Date** object setup at the value of zero UNIX time
     */
    fun getDateFromISO8601String(inputDate: String?): Date {
        try {
            val date = iso8601.parse(inputDate)
            return Date(date.time + TimeZone.getDefault().getOffset(Date().time))
        } catch (e: Exception) {
            return Date(0)
        }

    }

}
