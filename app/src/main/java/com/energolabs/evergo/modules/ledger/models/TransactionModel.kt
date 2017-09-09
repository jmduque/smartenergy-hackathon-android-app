package com.energolabs.evergo.modules.ledger.models

import com.energolabs.evergo.models.BaseModel

/**
 * Created by Jose Duque on 1/10/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

open class TransactionModel : BaseModel() {

    var type: String? = null
    var amount: Long = 0
    var currencySymbol: String? = null

}
