package com.energolabs.evergo.modules.currencyWallet.adapters

import com.energolabs.evergo.modules.currencyWallet.viewModels.WalletTransactionViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 8/30/2016.
 *
 *
 * Adapter for BuildingList.
 */
class WalletTransactionListAdapter(
        items: List<WalletTransactionViewModel>
) : FlexibleAdapter<WalletTransactionViewModel>(items)
