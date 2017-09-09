package com.energolabs.evergo.modules.ledger.viewHolders

import android.app.Activity
import android.view.View

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.currencyWallet.models.WalletTransactionModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose Duque on 9/11/2015.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LedgerWalletTransactionViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : LedgerBaseTransactionViewHolder<WalletTransactionModel>(
        view,
        activity,
        flexibleAdapter
) {

    override fun updateTransactionType(type: String?) {
        when (type) {
            WalletTransactionModel.TOPUP ->
                iv_icon?.setImageResource(R.drawable.ic_recharge_transaction_icon)
            WalletTransactionModel.WITHDRAW ->
                iv_icon?.setImageResource(R.drawable.ic_withdraw_transaction_icon)
            else ->
                iv_icon?.setImageResource(R.drawable.ic_recharge_transaction_icon)
        }
    }

    override fun updateDescription() {
        tv_description?.visibility = View.GONE
    }

}
