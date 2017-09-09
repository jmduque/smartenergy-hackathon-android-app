package com.energolabs.evergo.modules.settings.adapters

import com.energolabs.evergo.modules.settings.viewModels.SettingsGroupViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 8/30/2016.
 *
 *
 * Adapter for BuildingList.
 */
class SettingsGroupListAdapter(
        items: List<SettingsGroupViewModel>
) : FlexibleAdapter<SettingsGroupViewModel>(items) {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
