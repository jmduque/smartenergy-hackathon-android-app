package com.energolabs.evergo.modules.wallet.models

import com.energolabs.evergo.models.BaseModel

/**
 * Created by Jose Duque on 12/14/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

open class ExchangeModel : BaseModel() {

    var type: String? = null
    var amount: Long = 0
    var symbol: String? = null
    var paymentPassword: String? = null
}
