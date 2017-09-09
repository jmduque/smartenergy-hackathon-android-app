package com.energolabs.evergo.modules.devices.fragments

import android.os.Bundle
import android.view.View
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.ViewsController
import com.energolabs.evergo.modules.devices.adapters.SmartMeterListAdapter
import com.energolabs.evergo.modules.devices.models.DeviceInfo
import com.energolabs.evergo.modules.devices.requests.GetDevicesRequest
import com.energolabs.evergo.modules.devices.viewModels.SmartMeterViewModel
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

class SmartMeterListFragment : BaseListFragment<
        SmartMeterListAdapter,
        SmartMeterViewModel,
        DeviceInfo>(),
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
        setTitle(R.string.energo_smart_meter_device_list_title)
        requestData()
    }

    override fun makeAdapter(): SmartMeterListAdapter {
        return SmartMeterListAdapter(
                ArrayList<SmartMeterViewModel>()
        )
    }

    override val layoutId: Int
        get() = R.layout.fragment_smart_meter_list

    override val emptyListViewId: Int = R.id.ll_empty_list

    override fun requestData() {
        GetDevicesRequest
                .Builder(context ?: return)
                .setType("smart_meter")
                .setTag(BaseListFragment.GET_ITEMS)
                .setResultListener(this)
                .request()
    }

    override fun loadMoreData() {
        GetDevicesRequest
                .Builder(context ?: return)
                .setType("smart_meter")
                .setOffset(listedItemsCount)
                .setTag(BaseListFragment.GET_MORE_ITEMS)
                .setResultListener(this)
                .request()
    }

    override fun makeViewModel(item: DeviceInfo?): SmartMeterViewModel {
        return SmartMeterViewModel(
                activity,
                item
        )
    }

    override fun updateList(itemsList: List<DeviceInfo>?) {
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
                onAddSmartMeterClicked()
            }
        }
    }

    private fun onAddSmartMeterClicked() {
        DetailActivityNoCollapsing.openWithFragment(
                context,
                SmartMeterQRScannerFragment::class.java.name,
                SmartMeterQRScannerFragment.makeArguments(),
                true
        )
    }

    companion object {

        fun makeArguments(): Bundle? {
            return null
        }
    }

}
