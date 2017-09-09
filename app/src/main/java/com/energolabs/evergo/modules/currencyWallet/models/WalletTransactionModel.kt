package com.energolabs.evergo.modules.currencyWallet.models

import com.energolabs.evergo.modules.ledger.models.TransactionModel

/**
 * Created by Jose Duque on 12/13/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class WalletTransactionModel : TransactionModel() {

    companion object {
        val TOPUP = "topup"
        val WITHDRAW = "withdraw"
    }

}
