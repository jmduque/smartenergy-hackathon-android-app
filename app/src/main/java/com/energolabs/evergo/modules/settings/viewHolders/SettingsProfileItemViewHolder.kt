package com.energolabs.evergo.modules.settings.viewHolders

import android.app.Activity
import android.text.TextUtils
import android.view.View
import com.energolabs.evergo.modules.settings.models.SettingsProfileItem
import com.energolabs.evergo.modules.user.profile.fragments.UserProfileFragment
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class SettingsProfileItemViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseSettingsViewHolder<SettingsProfileItem>(
        view,
        activity,
        flexibleAdapter
) {

    override fun findViews(view: View) {
        super.findViews(view)
    }

    override fun updateView() {
        super.updateView()
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        val id = item?._id
        if (TextUtils.isEmpty(id)) {
            return
        }

        when (id) {
            else -> {
                DetailActivityNoCollapsing.openWithFragment(
                        activity ?: return,
                        UserProfileFragment::class.java.name,
                        UserProfileFragment.makeArguments(),
                        true
                )
            }
        }
    }

}
