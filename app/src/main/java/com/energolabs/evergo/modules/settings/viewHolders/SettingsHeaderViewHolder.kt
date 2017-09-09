package com.energolabs.evergo.modules.settings.viewHolders

import android.app.Activity
import android.view.View
import com.energolabs.evergo.modules.settings.models.SettingsHeaderItem
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class SettingsHeaderViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseSettingsViewHolder<SettingsHeaderItem>(
        view,
        activity,
        flexibleAdapter
)
