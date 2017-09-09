package com.energolabs.evergo.modules.ledger.fragments

import android.os.Bundle
import com.energolabs.evergo.modules.ledger.adapters.LedgerTransactionListAdapter
import com.energolabs.evergo.modules.ledger.models.TransactionWrapperModel
import com.energolabs.evergo.modules.ledger.requests.GetLedgerTransactionsRequest
import com.energolabs.evergo.modules.ledger.viewModels.LedgerTransactionViewModel
import com.energolabs.evergo.modules.ledger.viewModels.LedgerWalletTransactionViewModel
import com.energolabs.evergo.ui.fragments.BaseListFragment
import com.energolabs.evergo.ui.viewModels.BaseViewModel
import java.util.*

/**
 * Created by Jose Duque on 1/9/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class LedgerTransactionsListFragment : BaseListFragment<
        LedgerTransactionListAdapter,
        BaseViewModel<*, *>,
        TransactionWrapperModel
        >() {

    private var label: String? = null
    private var startDate: String? = null
    private var endDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        label = arguments?.getString(LABEL)
        startDate = arguments?.getString(START_DATE)
        endDate = arguments?.getString(END_DATE)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        setTitle(label)
    }

    override fun makeAdapter(): LedgerTransactionListAdapter {
        return LedgerTransactionListAdapter(
                ArrayList<BaseViewModel<*, *>>()
        )
    }

    override fun requestData() {
        GetLedgerTransactionsRequest.Builder(context ?: return)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setResultListener(this)
                .setTag(BaseListFragment.GET_ITEMS)
                .request()
    }

    override fun loadMoreData() {
        GetLedgerTransactionsRequest.Builder(context ?: return)
                .setOffset(listedItemsCount)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setResultListener(this)
                .setTag(BaseListFragment.GET_MORE_ITEMS)
                .request()
    }

    override val listedItemsCount: Int
        get() {
            return adapter?.getItemCountOfTypes(
                    LedgerWalletTransactionViewModel(activity, null).layoutRes,
                    LedgerTransactionViewModel(activity, null).layoutRes
            ) ?: 0
        }

    override fun makeViewModel(item: TransactionWrapperModel?): BaseViewModel<*, *> {
        return LedgerTransactionListAdapter.makeViewModel(
                activity,
                item
        )
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    companion object {

        private val LABEL = "label"
        private val START_DATE = "start_date"
        private val END_DATE = "end_date"

        fun makeArguments(
                label: String?,
                startDate: String?,
                endDate: String?
        ): Bundle {
            val args = Bundle()
            args.putString(
                    LABEL,
                    label
            )
            args.putString(
                    START_DATE,
                    startDate
            )
            args.putString(
                    END_DATE,
                    endDate
            )
            return args
        }
    }
}
