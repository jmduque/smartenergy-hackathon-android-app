package com.energolabs.evergo.modules.ledger.viewModels

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.ledger.models.LedgerSummaryModel
import com.energolabs.evergo.modules.ledger.viewHolders.LedgerSeeAllViewHolder
import com.energolabs.evergo.ui.viewModels.BaseViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/24/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LedgerSeeAllViewModel(
        activity: Activity?,
        item: LedgerSummaryModel?
) : BaseViewModel<LedgerSeeAllViewHolder, LedgerSummaryModel>(
        activity,
        item
) {

    override fun getLayoutRes(): Int {
        return R.layout.item_see_all
    }

    override fun makeViewHolder(
            adapter: FlexibleAdapter<*>,
            inflater: LayoutInflater,
            parent: ViewGroup
    ): LedgerSeeAllViewHolder {
        return LedgerSeeAllViewHolder(
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
