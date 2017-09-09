package com.energolabs.evergo.modules.currencyWallet.viewHolders

import android.app.Activity
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.currencyWallet.controllers.CurrencyController
import com.energolabs.evergo.modules.currencyWallet.models.WalletTransactionModel
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
class WalletTransactionViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder<WalletTransactionModel>(
        view,
        activity,
        flexibleAdapter,
        false
) {

    companion object {
        private val transactionTime = SimpleDateFormat(
                "MM-dd\nHH:mm",
                Locale.ENGLISH
        )
    }

    private var tv_date: TextView? = null
    private var iv_icon: ImageView? = null
    private var tv_value: TextView? = null


    override fun findViews(view: View) {
        super.findViews(view)
        tv_date = view.findViewById(R.id.tv_date) as TextView
        iv_icon = view.findViewById(R.id.iv_icon) as ImageView
        tv_value = view.findViewById(R.id.tv_value) as TextView
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
                item?.amount ?: 0,
                item?.currencySymbol,
                item?.type
        )
    }

    private fun updateTransactionDate(createdAt: String?) {
        val date = DateUtils.getDateFromISO8601String(createdAt)
        tv_date?.text = transactionTime.format(date)
    }

    private fun updateTransactionType(type: String?) {
        if (WalletTransactionModel.TOPUP == type) {
            iv_icon?.setImageResource(R.drawable.ic_recharge_transaction_icon)
        } else if (WalletTransactionModel.WITHDRAW == type) {
            iv_icon?.setImageResource(R.drawable.ic_withdraw_transaction_icon)
        } else {
            iv_icon?.setImageResource(R.drawable.ic_recharge_transaction_icon)
        }
    }

    private fun getTransactionSign(
            type: String?,
            amount: Long
    ): String {
        when (type) {
            WalletTransactionModel.TOPUP -> return "+"
            WalletTransactionModel.WITHDRAW -> return "-"
            else -> return if (amount > 0) "+" else "-"
        }
    }

    private fun updateTransactionValue(
            amount: Long,
            currencySymbol: String?,
            type: String?
    ) {
        tv_value?.text = resources?.getString(
                R.string.energo_wallet_transaction_value_format,
                getTransactionSign(type, amount),
                CurrencyController.getRealValue(amount),
                if (TextUtils.isEmpty(currencySymbol)) "PHP" else currencySymbol
        )
    }

}
