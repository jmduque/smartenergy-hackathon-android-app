package com.energolabs.evergo.modules.settings.viewHolders

import android.app.Activity
import android.view.View
import android.widget.CompoundButton
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.settings.models.SettingsSwitchItem
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class SettingsSwitchViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseSettingsViewHolder<SettingsSwitchItem>(
        view,
        activity,
        flexibleAdapter
) {

    private var compoundButton: CompoundButton? = null

    override fun findViews(view: View) {
        super.findViews(view)
        compoundButton = view.findViewById(R.id.toggle) as CompoundButton
    }

    override fun updateView() {
        super.updateView()
        val item = item
        compoundButton?.isChecked = item?.isEnabled ?: false
    }

}
