package com.energolabs.evergo.modules.ledger.viewHolders

import android.app.Activity
import android.view.View

import com.energolabs.evergo.modules.ledger.fragments.LedgerTransactionsListFragment
import com.energolabs.evergo.modules.ledger.models.LedgerSummaryModel
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.viewHolders.BaseFlexibleViewHolder

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose Duque on 9/11/2015.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LedgerSeeAllViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder<LedgerSummaryModel>(
        view,
        activity,
        flexibleAdapter,
        false
) {

    override fun onClick(view: View?) {
        super.onClick(view)
        activity?.startActivity(
                DetailActivityNoCollapsing.makeIntent(
                        activity ?: return,
                        LedgerTransactionsListFragment::class.java.name,
                        LedgerTransactionsListFragment.makeArguments(
                                item?.label,
                                item?.startDate,
                                item?.endDate
                        ),
                        true
                )
        )
    }

}
