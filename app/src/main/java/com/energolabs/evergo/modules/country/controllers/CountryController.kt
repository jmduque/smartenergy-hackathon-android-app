package com.energolabs.evergo.modules.currencyWallet.controllers

import android.support.annotation.DrawableRes
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.country.models.CountryModel

/**
 * Created by Jose Duque on 4/27/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

object CountryController {

    @DrawableRes fun makeDefaultFlag(phoneCode: String?): Int {
        when (phoneCode) {
            "+86" -> return R.drawable.ic_china
            "+886" -> return R.drawable.ic_taiwan
            "+63" -> return R.drawable.ic_philippines
            "+1" -> return R.drawable.ic_usa
            else -> return R.drawable.ic_china
        }
    }

    fun makeDefaultCountry() : CountryModel {
        val countryModel = CountryModel()
        countryModel.name = "Philippines"
        countryModel.phoneCode = "+63"
        return countryModel
    }

}
