package com.energolabs.evergo.modules.ledger.viewModels

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.ledger.models.LedgerSummaryModel
import com.energolabs.evergo.modules.ledger.viewHolders.LedgerSummaryViewHolder
import com.energolabs.evergo.ui.viewModels.BaseViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/24/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LedgerSummaryViewModel(
        activity: Activity?,
        item: LedgerSummaryModel?
) : BaseViewModel<LedgerSummaryViewHolder, LedgerSummaryModel>(
        activity,
        item
) {

    override fun getLayoutRes(): Int {
        return R.layout.item_ledger_summary
    }

    override fun bindViewHolder(
            adapter: FlexibleAdapter<*>?,
            viewHolder: LedgerSummaryViewHolder?,
            position: Int,
            payloads: List<*>?
    ) {
        super.bindViewHolder(
                adapter,
                viewHolder,
                position,
                payloads
        )
        viewHolder?.viewModel = this
        viewHolder?.updateTransactionList(
                expanded,
                false
        )
    }

    override fun makeViewHolder(
            adapter: FlexibleAdapter<*>,
            inflater: LayoutInflater,
            parent: ViewGroup
    ): LedgerSummaryViewHolder {
        return LedgerSummaryViewHolder(
                inflater.inflate(
                        layoutRes,
                        parent,
                        false
                ),
                activity,
                adapter
        )
    }

}
