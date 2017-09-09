package com.energolabs.evergo.modules.battery.fragments

import android.os.Bundle
import android.view.View
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.ViewsController
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.battery.adapters.BatteryListAdapter
import com.energolabs.evergo.modules.battery.models.BatteryModel
import com.energolabs.evergo.modules.battery.requests.GetBatteriesRequest
import com.energolabs.evergo.modules.battery.viewModels.BatteryViewModel
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseListFragment
import com.energolabs.evergo.utils.CollectionUtils
import java.util.*

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class BatteryListFragment : BaseListFragment<
        BatteryListAdapter,
        BatteryViewModel,
        BatteryModel>(),
        View.OnClickListener {

    private var fab_add: View? = null
    private var fab_empty_add: View? = null

    override fun findViews(view: View) {
        super.findViews(view)
        fab_add = view.findViewById(R.id.fab_add)
        fab_empty_add = view.findViewById(R.id.fab_empty_add)
    }

    override fun setListeners() {
        super.setListeners()
        fab_add?.setOnClickListener(this)
        fab_empty_add?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_battery_list_title)
        requestData()
    }

    override fun makeAdapter(): BatteryListAdapter {
        return BatteryListAdapter(
                ArrayList()
        )
    }

    override val layoutId: Int
        get() = R.layout.fragment_battery_list

    override val emptyListViewId: Int = R.id.ll_empty_list

    override fun requestData() {
        GetBatteriesRequest.Builder(context ?: return)
                .setOwner(
                        AuthPreferences(activity).userId
                )
                .setTag(GET_ITEMS)
                .setResultListener(this)
                .request()
    }

    override fun loadMoreData() {
        GetBatteriesRequest.Builder(context ?: return)
                .setOwner("smart_meter")
                .setTag(GET_MORE_ITEMS)
                .setResultListener(this)
                .request()
    }

    override fun makeViewModel(item: BatteryModel?): BatteryViewModel {
        return BatteryViewModel(
                activity,
                item
        )
    }

    override fun updateList(itemsList: List<BatteryModel>?) {
        super.updateList(itemsList)
        ViewsController.setVisible(
                !CollectionUtils.isEmpty(itemsList),
                fab_add
        )
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_add, R.id.fab_empty_add -> {
                onAddBatteryClicked()
            }
        }
    }

    private fun onAddBatteryClicked() {
        DetailActivityNoCollapsing.openWithFragment(
                context,
                AddBatteryFragment::class.java.name,
                AddBatteryFragment.makeArguments(),
                true
        )
    }

    companion object {

        fun makeArguments(): Bundle? {
            return null
        }
    }

}