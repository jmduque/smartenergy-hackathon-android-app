package com.energolabs.evergo.modules.settings.viewHolders

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.settings.adapters.SettingsItemListAdapter
import com.energolabs.evergo.modules.settings.models.*
import com.energolabs.evergo.modules.settings.viewModels.*
import com.energolabs.evergo.ui.viewHolders.BaseFlexibleViewHolder
import com.energolabs.evergo.ui.viewModels.BaseViewModel
import com.energolabs.evergo.utils.CollectionUtils
import eu.davidea.flexibleadapter.FlexibleAdapter
import java.util.*

/**
 * Created by Jose Duque on 9/11/2015.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class SettingsGroupViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder<SettingsGroup>(view, activity, flexibleAdapter, false) {

    private var rv_items: RecyclerView? = null
    private var adapter: SettingsItemListAdapter? = null


    override fun findViews(view: View) {
        super.findViews(view)
        rv_items = view.findViewById(R.id.rv_items) as RecyclerView
        rv_items?.layoutManager = LinearLayoutManager(activity)
    }

    override fun setListeners() {
        super.setListeners()
    }

    override fun updateView() {
        super.updateView()
        assertAdapter()
        updateViewModels(item?.items)
    }

    private fun assertAdapter() {
        if (adapter != null) {
            return
        }

        adapter = SettingsItemListAdapter(
                ArrayList<BaseViewModel<*, *>>()
        )
        rv_items?.adapter = adapter
    }

    private fun updateViewModels(items: List<SettingsItem>?) {
        if (CollectionUtils.isEmpty(items)) {
            adapter?.updateDataSet(null)
            return
        }

        val viewModels = ArrayList<BaseViewModel<*, *>>()
        items?.forEach {
            when (it) {
                is SettingsHeaderItem -> viewModels.add(
                        SettingsHeaderItemViewModel(
                                activity,
                                it
                        )
                )
                is SettingsSwitchItem -> viewModels.add(
                        SettingsSwitchItemViewModel(
                                activity,
                                it
                        )
                )
                is SettingsLogoutItem -> viewModels.add(
                        SettingsLogoutItemViewModel(
                                activity,
                                it
                        )
                )
                is SettingsProfileItem -> viewModels.add(
                        SettingsProfileItemViewModel(
                                activity,
                                it
                        )
                )
                else -> viewModels.add(
                        SettingsItemViewModel(
                                activity,
                                it
                        )
                )
            }
        }

        adapter?.updateDataSet(viewModels)
    }

}
