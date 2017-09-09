package com.energolabs.evergo.modules.settings.viewHolders

import android.app.Activity
import android.text.TextUtils
import android.view.View
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.password.fragments.ChangePasswordFragment
import com.energolabs.evergo.modules.password.fragments.ChangePaymentPasswordFragment
import com.energolabs.evergo.modules.password.fragments.SetPaymentPasswordFragment
import com.energolabs.evergo.modules.settings.models.SettingsItem
import com.energolabs.evergo.modules.user.profile.fragments.UserProfileFragment
import com.energolabs.evergo.modules.user.profile.storage.UserProfilePreferences
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class SettingsItemViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseSettingsViewHolder<SettingsItem>(
        view,
        activity,
        flexibleAdapter
) {

    override fun onClick(view: View?) {
        super.onClick(view)
        val settingsItem = item ?: return

        val id = settingsItem._id
        if (TextUtils.isEmpty(id)) {
            return
        }

        when (id) {
            "profile" -> {
                DetailActivityNoCollapsing.openWithFragment(
                        activity ?: return,
                        UserProfileFragment::class.java.name,
                        UserProfileFragment.makeArguments(),
                        true
                )
            }
            "password" -> {
                DetailActivityNoCollapsing.openWithFragment(
                        activity ?: return,
                        ChangePasswordFragment::class.java.name,
                        ChangePasswordFragment.makeArguments(),
                        true
                )
            }
            "paymentPassword" -> {
                val authPreferences = AuthPreferences(activity ?: return)
                val userProfilePreferences = UserProfilePreferences(
                        activity ?: return,
                        authPreferences.userId
                )
                if (userProfilePreferences.hasPaymentPassword) {
                    DetailActivityNoCollapsing.openWithFragment(
                            activity ?: return,
                            ChangePaymentPasswordFragment::class.java.name,
                            ChangePaymentPasswordFragment.makeArguments(),
                            true
                    )
                } else {
                    DetailActivityNoCollapsing.openWithFragment(
                            activity ?: return,
                            SetPaymentPasswordFragment::class.java.name,
                            SetPaymentPasswordFragment.makeArguments(),
                            true
                    )
                }
            }
        }
    }

}
