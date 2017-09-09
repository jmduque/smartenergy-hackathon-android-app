package com.energolabs.evergo.modules.country.viewModels

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.country.models.CountryModel
import com.energolabs.evergo.modules.country.viewHolders.CountryModelViewHolder
import com.energolabs.evergo.ui.viewModels.BaseViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/24/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class CountryModelViewModel(
        activity: Activity?,
        item: CountryModel?
) : BaseViewModel<CountryModelViewHolder, CountryModel>(
        activity,
        item
) {

    override fun getLayoutRes(): Int {
        return R.layout.item_country
    }

    override fun makeViewHolder(
            adapter: FlexibleAdapter<*>,
            inflater: LayoutInflater,
            parent: ViewGroup
    ): CountryModelViewHolder {
        return CountryModelViewHolder(
                inflater.inflate(
                        layoutRes,
                        parent,
                        false
                ),
                activity,
                adapter
        )
    }

}
