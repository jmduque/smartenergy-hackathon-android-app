package com.energolabs.evergo.modules.ledger.models

import com.energolabs.evergo.models.BaseModel

/**
 * Created by Jose Duque on 1/9/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class LedgerSummaryModel : BaseModel() {

    var label: String? = null
    var purchases: Long = 0
    var sells: Long = 0
    var others: Long = 0
    var startDate: String? = null
    var endDate: String? = null
    var transactionList: List<TransactionWrapperModel>? = null

}
