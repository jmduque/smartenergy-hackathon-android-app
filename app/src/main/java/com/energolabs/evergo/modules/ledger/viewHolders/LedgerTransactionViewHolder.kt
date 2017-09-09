package com.energolabs.evergo.modules.ledger.viewHolders

import android.app.Activity
import android.view.View
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.ledger.models.TransactionModel
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose Duque on 9/11/2015.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LedgerTransactionViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : LedgerBaseTransactionViewHolder<TransactionModel>(
        view,
        activity,
        flexibleAdapter
) {

    override fun updateTransactionType(type: String?) {
        iv_icon?.setImageResource(
                R.drawable.ic_others_transaction
        )
    }

    override fun updateDescription() {
        tv_description?.visibility = View.GONE
    }

}
