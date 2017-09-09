package com.energolabs.evergo.modules.settings.viewHolders

import android.app.Activity
import android.view.View
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.login.fragments.LoginFragment
import com.energolabs.evergo.modules.settings.models.SettingsLogoutItem
import com.energolabs.evergo.ui.activities.DetailActivityNoToolbar
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class SettingsLogoutItemViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseSettingsViewHolder<SettingsLogoutItem>(view, activity, flexibleAdapter) {

    override fun onClick(view: View?) {
        super.onClick(view)
        AuthPreferences(activity ?: return).cleanPreferences()
        DetailActivityNoToolbar.openWithFragment(
                activity ?: return,
                LoginFragment::class.java.name,
                LoginFragment.makeArguments(),
                true
        )
        activity?.finish()
    }

}
