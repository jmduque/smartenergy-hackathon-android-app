package com.energolabs.evergo.modules.country

/**
 * Created by Jose Duque on 1/23/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

interface CountrySelector {
    companion object {
        val SELECT_COUNTRY = 1000
        val SELECT_COUNTRY_RESULT = 2000
        val SELECT_COUNTRY_CANCEL = 3000

        val COUNTRY = "country"
    }
}
