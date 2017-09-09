package com.energolabs.evergo.modules.settings.viewHolders

import android.app.Activity
import android.view.View
import android.widget.TextView

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.settings.models.SettingsItem
import com.energolabs.evergo.ui.viewHolders.BaseFlexibleViewHolder

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
abstract class BaseSettingsViewHolder<DM : SettingsItem>(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder<DM>(
        view,
        activity,
        flexibleAdapter,
        false
) {

    private var name: TextView? = null

    override fun findViews(view: View) {
        super.findViews(view)
        name = view.findViewById(R.id.tv_name) as TextView
    }

    override fun updateView() {
        super.updateView()
        name?.text = item?.name
    }
}
