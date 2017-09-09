package com.energolabs.evergo.modules.ledger.models.JsonDeserializer

import com.energolabs.evergo.modules.currencyWallet.models.WalletTransactionModel
import com.energolabs.evergo.modules.ledger.models.TransactionModel
import com.energolabs.evergo.modules.ledger.models.TransactionWrapperModel
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

/**
 * Created by Jose Duque on 1/10/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class TransactionWrapperModelJsonDeserializer : JsonDeserializer<TransactionWrapperModel> {

    @Throws(JsonParseException::class)
    override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
    ): TransactionWrapperModel {
        val jsonObject = json.asJsonObject
        val transactionWrapperModel = TransactionWrapperModel()

        val type = jsonObject.get("type").asString
        transactionWrapperModel.type = type

        val transactionJsonObject = jsonObject.get("transaction").asJsonObject
        when (type) {
            TransactionWrapperModel.WALLET_TRANSACTION ->
                transactionWrapperModel.transaction = context.deserialize<Any>(
                        transactionJsonObject,
                        WalletTransactionModel::class.java
                ) as TransactionModel
            else ->
                transactionWrapperModel.transaction = context.deserialize<Any>(
                        transactionJsonObject,
                        TransactionModel::class.java
                ) as TransactionModel
        }

        return transactionWrapperModel
    }

}