package com.energolabs.evergo.modules.ledger.adapters

import android.app.Activity
import com.energolabs.evergo.modules.currencyWallet.models.WalletTransactionModel
import com.energolabs.evergo.modules.ledger.models.TransactionWrapperModel
import com.energolabs.evergo.modules.ledger.viewModels.LedgerTransactionViewModel
import com.energolabs.evergo.modules.ledger.viewModels.LedgerWalletTransactionViewModel
import com.energolabs.evergo.ui.viewModels.BaseViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 8/30/2016.
 *
 *
 * Adapter for BuildingList.
 */
class LedgerTransactionListAdapter(
        items: List<BaseViewModel<*, *>>
) : FlexibleAdapter<BaseViewModel<*, *>>(items) {

    companion object {
        fun makeViewModel(
                activity: Activity?,
                item: TransactionWrapperModel?
        ): BaseViewModel<*, *> {
            when (item?.type) {
                TransactionWrapperModel.WALLET_TRANSACTION ->
                    return LedgerWalletTransactionViewModel(
                            activity,
                            item.transaction as WalletTransactionModel?
                    )
                else ->
                    return LedgerTransactionViewModel(
                            activity,
                            item?.transaction
                    )
            }
        }
    }

}
