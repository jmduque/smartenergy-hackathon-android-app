package com.energolabs.evergo.modules.currencyWallet.controllers

/**
 * Created by Jose Duque on 4/27/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

object CurrencyController {

    val CURRENCY_RATIO = 1000000.0

    fun getRealValue(rawValue: Long): Double {
        return rawValue / CURRENCY_RATIO
    }

    fun getRawValue(realValue: Double): Long {
        return (realValue * CURRENCY_RATIO).toLong()
    }

}
