package com.energolabs.evergo.modules.location.viewHolders

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.location.LocationSelector
import com.energolabs.evergo.modules.location.models.LocationModel
import com.energolabs.evergo.ui.viewHolders.BaseFlexibleViewHolder

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose Duque on 9/11/2015.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LocationModelViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder<LocationModel>(
        view,
        activity,
        flexibleAdapter,
        false
), LocationSelector {

    private var tv_name: TextView? = null

    override fun findViews(view: View) {
        super.findViews(view)
        tv_name = view.findViewById(R.id.tv_name) as TextView
    }

    override fun setListeners() {
        super.setListeners()
        rootView.setOnClickListener(this)
    }

    override fun updateView() {
        super.updateView()
        updateCountryName(
                item?.areaName
        )
    }

    private fun updateCountryName(name: String?) {
        tv_name?.text = name
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            else -> {
                val data = Intent()
                data.putExtra(
                        LocationSelector.LOCATION,
                        item
                )
                activity?.setResult(
                        LocationSelector.SELECT_LOCATION_RESULT,
                        data
                )
                activity?.finish()
            }
        }
    }

}
