package com.energolabs.evergo.modules.energyWallet.controllers

/**
 * Created by Jose Duque on 4/27/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

object EnergyController {

    val ENERGY_RATIO = 1000.0

    fun getRealValue(rawValue: Long): Double {
        return rawValue / ENERGY_RATIO
    }

    fun getRawValue(realValue: Double): Long {
        return (realValue * ENERGY_RATIO).toLong()
    }

}
