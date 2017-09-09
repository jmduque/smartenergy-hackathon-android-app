package com.energolabs.evergo.modules.settings.viewModels

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.settings.models.SettingsGroup
import com.energolabs.evergo.modules.settings.viewHolders.SettingsGroupViewHolder
import com.energolabs.evergo.ui.viewModels.BaseViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/24/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class SettingsGroupViewModel(
        activity: Activity?,
        item: SettingsGroup?
) : BaseViewModel<SettingsGroupViewHolder, SettingsGroup>(
        activity,
        item
) {

    override fun getLayoutRes(): Int {
        return R.layout.item_settings_group
    }

    override fun makeViewHolder(
            adapter: FlexibleAdapter<*>,
            inflater: LayoutInflater,
            parent: ViewGroup
    ): SettingsGroupViewHolder {
        return SettingsGroupViewHolder(
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
