package com.energolabs.evergo.modules.transactions.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.FragmentPermissionsController
import com.energolabs.evergo.modules.devices.fragments.SmartMeterListFragment
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class TransactionListFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_transactions_list

    override fun findViews(view: View) {
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_transactions_list_title)
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