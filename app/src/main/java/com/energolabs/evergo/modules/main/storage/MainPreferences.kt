package com.energolabs.evergo.modules.main.storage

import android.content.Context
import android.content.SharedPreferences

import com.energolabs.evergo.modules.main.models.MainModel

/**
 * Created by Jose Duque on 12/2/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class MainPreferences(
        context: Context,
        userId: String?
) {

    private val PACKAGE_NAME = "com.energolabs.evergo.modules.main"
    private val ENERGY_BALANCE = "energy_balance"
    private val ENERGY_CREATION = "energy_creation"
    private val ENERGY_USAGE = "energy_usage"
    private val MARKET_PRICE = "market_price"

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(
                PACKAGE_NAME + "." + userId,
                Context.MODE_PRIVATE
        )
    }

    fun cleanPreferences() {
        sharedPreferences.edit()?.clear()?.apply()
    }

    fun saveEnergyBalance(
            value: Long?
    ) {
        val editor = sharedPreferences.edit()
        editor?.putLong(
                ENERGY_BALANCE,
                value ?: 0
        )
        editor?.apply()
    }

    val energyBalance: Long
        get() = sharedPreferences.getLong(
                ENERGY_BALANCE,
                0
        )

    fun saveEnergyCreation(value: Long?) {
        val editor = sharedPreferences.edit()
        editor?.putLong(
                ENERGY_CREATION,
                value ?: 0
        )
        editor?.apply()
    }

    val energyCreation: Long
        get() = sharedPreferences.getLong(
                ENERGY_CREATION,
                0
        )

    fun saveEnergyUsage(value: Long?) {
        val editor = sharedPreferences.edit()
        editor?.putLong(
                ENERGY_USAGE,
                value ?: 0
        )
        editor?.apply()
    }

    val energyUsage: Long
        get() = sharedPreferences.getLong(
                ENERGY_USAGE,
                0
        )

    fun saveMarketPrice(value: Long?) {
        val editor = sharedPreferences.edit()
        editor?.putLong(
                MARKET_PRICE,
                value ?: 0
        )
        editor?.apply()
    }

    val marketPrice: Long
        get() = sharedPreferences.getLong(
                MARKET_PRICE,
                0
        )

    fun saveMainModel(model: MainModel?) {
        saveEnergyBalance(model?.energyBalance)
        saveEnergyCreation(model?.energyCreated)
        saveEnergyUsage(model?.energyUsed)
        saveMarketPrice(model?.marketPrice)
    }

    val mainModel: MainModel
        get() {
            val mainModel = MainModel()
            mainModel.energyBalance = energyBalance
            mainModel.energyCreated = energyCreation
            mainModel.energyUsed = energyUsage
            mainModel.marketPrice = marketPrice
            return mainModel
        }

}
