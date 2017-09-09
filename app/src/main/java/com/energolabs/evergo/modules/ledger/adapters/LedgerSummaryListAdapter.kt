package com.energolabs.evergo.modules.ledger.adapters

import com.energolabs.evergo.modules.ledger.viewModels.LedgerSummaryViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LedgerSummaryListAdapter(
        items: List<LedgerSummaryViewModel>
) : FlexibleAdapter<LedgerSummaryViewModel>(items)
