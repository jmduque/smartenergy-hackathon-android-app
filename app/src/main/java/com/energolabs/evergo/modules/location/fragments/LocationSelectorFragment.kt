package com.energolabs.evergo.modules.location.fragments

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.View
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.location.adapters.LocationModelListAdapter
import com.energolabs.evergo.modules.location.models.LocationModel
import com.energolabs.evergo.modules.location.requests.GetLocationsRequest
import com.energolabs.evergo.modules.location.viewModels.LocationModelViewModel
import com.energolabs.evergo.ui.fragments.BaseListFragment
import java.util.*

/**
 * Created by Jose Duque on 1/23/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class LocationSelectorFragment : BaseListFragment<
        LocationModelListAdapter,
        LocationModelViewModel,
        LocationModel
        >()
        , SearchView.OnQueryTextListener {

    private var sv_search: SearchView? = null

    private val items = ArrayList<LocationModel>()

    private var area: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        area = arguments?.getInt(
                AREA_CODE
        )
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_location_selector

    override fun findViews(view: View) {
        super.findViews(view)
        sv_search = view.findViewById(R.id.sv_search) as SearchView
    }

    override fun setListeners() {
        super.setListeners()
        sv_search?.setOnQueryTextListener(this)
    }

    override fun makeAdapter(): LocationModelListAdapter {
        return LocationModelListAdapter(
                ArrayList<LocationModelViewModel>()
        )
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_location_selector_title)
    }

    override fun requestData() {
        GetLocationsRequest
                .Builder(context ?: return)
                .setArea(area)
                .setResultListener(this)
                .setTag(BaseListFragment.GET_ITEMS)
                .request()
    }

    override fun loadMoreData() {
        GetLocationsRequest
                .Builder(context ?: return)
                .setArea(area)
                .setOffset(listedItemsCount)
                .setResultListener(this)
                .setTag(BaseListFragment.GET_MORE_ITEMS)
                .request()
    }

    override fun makeViewModel(item: LocationModel?): LocationModelViewModel {
        return LocationModelViewModel(
                activity,
                item
        )
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onResultSuccess(
            tag: Any?,
            response: List<LocationModel>?
    ) {
        if (response != null) {
            items.clear()
            items.addAll(response)
        }
        super.onResultSuccess(
                tag,
                response
        )
    }

    override fun onQueryTextSubmit(s: String): Boolean {
        return false
    }

    override fun onQueryTextChange(s: String): Boolean {
        val validItems = ArrayList<LocationModel>()
        for (i in items.indices) {
            val item = items[i]
            if (TextUtils.isEmpty(s)) {
                validItems.add(item)
                continue
            }

            if (TextUtils.indexOf(item.areaName?.toLowerCase(Locale.getDefault()), s.toLowerCase(Locale.getDefault())) >= 0) {
                validItems.add(item)
                continue
            }

            if (TextUtils.indexOf(item.areaCode.toString(), s) >= 0) {
                validItems.add(item)
            }
        }
        onGetItemsSuccess(
                validItems
        )
        return false
    }

    companion object {

        private val AREA_CODE = "area_code"

        fun makeArguments(
                areaCode: Int?
        ): Bundle {
            val args = Bundle()
            if (areaCode != null) {
                args.putInt(
                        AREA_CODE,
                        areaCode
                )
            }
            return args
        }
    }
}
