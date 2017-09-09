package com.energolabs.evergo.modules.settings.viewModels

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.settings.models.SettingsLogoutItem
import com.energolabs.evergo.modules.settings.viewHolders.SettingsLogoutItemViewHolder
import com.energolabs.evergo.ui.viewModels.BaseViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class SettingsLogoutItemViewModel(
        activity: Activity?,
        item: SettingsLogoutItem?
) : BaseViewModel<SettingsLogoutItemViewHolder, SettingsLogoutItem>(
        activity,
        item
) {

    override fun getLayoutRes(): Int {
        return R.layout.item_settings_logout_item
    }

    override fun makeViewHolder(
            adapter: FlexibleAdapter<*>,
            inflater: LayoutInflater,
            parent: ViewGroup
    ): SettingsLogoutItemViewHolder {
        return SettingsLogoutItemViewHolder(
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
