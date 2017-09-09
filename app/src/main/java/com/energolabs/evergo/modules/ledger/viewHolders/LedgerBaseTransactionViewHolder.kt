package com.energolabs.evergo.modules.ledger.viewHolders

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.currencyWallet.controllers.CurrencyController
import com.energolabs.evergo.modules.ledger.models.TransactionModel
import com.energolabs.evergo.ui.viewHolders.BaseFlexibleViewHolder
import com.energolabs.evergo.utils.DateUtils
import eu.davidea.flexibleadapter.FlexibleAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jose Duque on 9/11/2015.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
abstract class LedgerBaseTransactionViewHolder<VM : TransactionModel>(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder<VM>(
        view,
        activity,
        flexibleAdapter,
        false
) {

    private var tv_date: TextView? = null
    protected var iv_icon: ImageView? = null
    private var tv_value: TextView? = null
    protected var tv_description: TextView? = null

    override fun findViews(view: View) {
        super.findViews(view)
        tv_date = view.findViewById(R.id.tv_date) as TextView?
        iv_icon = view.findViewById(R.id.iv_icon) as ImageView?
        tv_value = view.findViewById(R.id.tv_value) as TextView?
        tv_description = view.findViewById(R.id.tv_description) as TextView?
    }

    override fun setListeners() {
        super.setListeners()
    }

    override fun updateView() {
        super.updateView()
        updateTransactionDate(
                item?.createdAt
        )
        updateTransactionType(
                item?.type
        )
        updateTransactionValue(
                getCurrencyAmount(),
                item?.currencySymbol
        )
        updateDescription()
    }

    open fun getCurrencyAmount(): Long {
        return item?.amount ?: 0
    }

    private fun updateTransactionDate(createdAt: String?) {
        val date = DateUtils.getDateFromISO8601String(createdAt)
        tv_date?.text = transactionTime.format(date)
    }

    protected abstract fun updateTransactionType(type: String?)

    private fun updateTransactionValue(
            amount: Long,
            currencySymbol: String?
    ) {
        tv_value?.text = resources?.getString(
                R.string.energo_wallet_transaction_value_format,
                if (amount > 0) "+" else "",
                CurrencyController.getRealValue(amount),
                currencySymbol
        )
    }

    protected abstract fun updateDescription()

    companion object {

        private val transactionTime = SimpleDateFormat(
                "MM-dd\nHH:mm",
                Locale.ENGLISH
        )
    }

}
