package com.energolabs.evergo.modules.settings.fragments

import android.os.Bundle
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.settings.adapters.SettingsGroupListAdapter
import com.energolabs.evergo.modules.settings.models.*
import com.energolabs.evergo.modules.settings.viewModels.SettingsGroupViewModel
import com.energolabs.evergo.modules.user.profile.storage.UserProfilePreferences
import com.energolabs.evergo.ui.fragments.BaseListFragment
import java.util.*

/**
 * Created by Jose Duque on 12/9/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class SettingsFragment : BaseListFragment<
        SettingsGroupListAdapter,
        SettingsGroupViewModel,
        SettingsGroup>() {

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_settings_title)
        requestData()
    }

    override fun makeAdapter(): SettingsGroupListAdapter {
        return SettingsGroupListAdapter(
                ArrayList<SettingsGroupViewModel>()
        )
    }

    override fun requestData() {
        val settingsGroups = ArrayList<SettingsGroup>()

        settingsGroups.add(
                makeMyAccountSettingsGroup()
        )

        settingsGroups.add(
                makeAppSettingsGroup()
        )

        settingsGroups.add(
                makeSecuritySettingsGroup()
        )

        settingsGroups.add(
                makeUpdateGroup()
        )

        settingsGroups.add(
                makeLogoutGroup()
        )

        onGetItemsSuccess(settingsGroups)
    }

    private fun makeMyAccountSettingsGroup(): SettingsGroup {
        val settingsGroup = SettingsGroup()
        val items = ArrayList<SettingsItem>()
        settingsGroup.items = items

        val myAccountItem = SettingsHeaderItem()
        myAccountItem.name = getStringSafely(R.string.energo_settings_my_account)
        myAccountItem._id = "my_account"
        items.add(myAccountItem)

        val profileItem = SettingsProfileItem()
        profileItem.name = getStringSafely(R.string.energo_settings_profile)
        profileItem._id = "profile"
        val authPreferences = AuthPreferences(context)
        val userProfilePreferences = UserProfilePreferences(
                context,
                authPreferences.userId
        )
        profileItem.userModel = userProfilePreferences.userModel
        items.add(profileItem)

        val contactItem = SettingsItem()
        contactItem.name = getStringSafely(R.string.energo_settings_contact)
        contactItem._id = "contact"
        items.add(contactItem)

        return settingsGroup
    }

    private fun makeAppSettingsGroup(): SettingsGroup {
        val settingsGroup = SettingsGroup()
        val items = ArrayList<SettingsItem>()
        settingsGroup.items = items

        val appSettingsItem = SettingsHeaderItem()
        appSettingsItem.name = getStringSafely(R.string.energo_settings_app_settings)
        appSettingsItem._id = "app_setting"
        items.add(appSettingsItem)

        val pushItem = SettingsSwitchItem()
        pushItem.name = getStringSafely(R.string.energo_settings_enable_push)
        pushItem._id = "enable_push"
        items.add(pushItem)

        return settingsGroup
    }

    private fun makeSecuritySettingsGroup(): SettingsGroup {
        val settingsGroup = SettingsGroup()
        val items = ArrayList<SettingsItem>()
        settingsGroup.items = items

        val securityItem = SettingsHeaderItem()
        securityItem.name = getStringSafely(R.string.energo_settings_security)
        securityItem._id = "security"
        items.add(securityItem)

        val passwordItem = SettingsItem()
        passwordItem.name = getStringSafely(R.string.energo_settings_password)
        passwordItem._id = "password"
        items.add(passwordItem)

        val paymentPasswordItem = SettingsItem()
        paymentPasswordItem.name = getStringSafely(R.string.energo_settings_payment_password)
        paymentPasswordItem._id = "paymentPassword"
        items.add(paymentPasswordItem)

        val phoneItem = SettingsItem()
        phoneItem.name = getStringSafely(R.string.energo_settings_phone)
        phoneItem._id = "phone"
        items.add(phoneItem)

        val helpItem = SettingsItem()
        helpItem.name = getStringSafely(R.string.energo_settings_help)
        helpItem._id = "help"
        items.add(helpItem)

        val legalItem = SettingsItem()
        legalItem.name = getStringSafely(R.string.energo_settings_legal)
        legalItem._id = "legal"
        items.add(legalItem)

        return settingsGroup
    }

    private fun makeUpdateGroup(): SettingsGroup {
        val settingsGroup = SettingsGroup()
        val items = ArrayList<SettingsItem>()
        settingsGroup.items = items

        val updateItem = SettingsItem()
        updateItem.name = getStringSafely(R.string.energo_settings_update)
        updateItem._id = "update"
        items.add(updateItem)

        return settingsGroup
    }

    private fun makeLogoutGroup(): SettingsGroup {
        val settingsGroup = SettingsGroup()
        val items = ArrayList<SettingsItem>()
        settingsGroup.items = items

        val logoutItem = SettingsLogoutItem()
        logoutItem.name = getStringSafely(R.string.energo_settings_logout)
        logoutItem._id = "logout"
        items.add(logoutItem)

        return settingsGroup
    }

    override fun loadMoreData() {
        adapter?.onLoadMoreComplete(null)
    }

    override fun makeViewModel(item: SettingsGroup?): SettingsGroupViewModel {
        return SettingsGroupViewModel(
                activity,
                item
        )
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
