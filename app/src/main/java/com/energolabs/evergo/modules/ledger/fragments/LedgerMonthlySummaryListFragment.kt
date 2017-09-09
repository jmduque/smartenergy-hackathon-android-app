package com.energolabs.evergo.modules.ledger.fragments

import android.os.Bundle
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.ledger.adapters.LedgerSummaryListAdapter
import com.energolabs.evergo.modules.ledger.models.LedgerSummaryModel
import com.energolabs.evergo.modules.ledger.requests.GetLedgerSummaryRequest
import com.energolabs.evergo.modules.ledger.viewModels.LedgerSummaryViewModel
import com.energolabs.evergo.ui.fragments.BaseListFragment
import java.util.*

/**
 * Created by Jose Duque on 1/9/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class LedgerMonthlySummaryListFragment : BaseListFragment<
        LedgerSummaryListAdapter,
        LedgerSummaryViewModel,
        LedgerSummaryModel
        >() {

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_ledger_title)
    }

    override fun makeAdapter(): LedgerSummaryListAdapter {
        return LedgerSummaryListAdapter(
                ArrayList<LedgerSummaryViewModel>()
        )
    }

    override fun requestData() {
        GetLedgerSummaryRequest
                .Builder(context ?: return)
                .setType("monthly")
                .setResultListener(this)
                .setTag(BaseListFragment.GET_ITEMS)
                .request()
    }

    override fun loadMoreData() {
        GetLedgerSummaryRequest
                .Builder(context ?: return)
                .setType("monthly")
                .setOffset(listedItemsCount)
                .setResultListener(this)
                .setTag(BaseListFragment.GET_MORE_ITEMS)
                .request()
    }

    override fun makeViewModel(item: LedgerSummaryModel?): LedgerSummaryViewModel {
        return LedgerSummaryViewModel(
                activity,
                item
        )
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    companion object {

        fun makeArguments(): Bundle? {
            return null
        }
    }
}
