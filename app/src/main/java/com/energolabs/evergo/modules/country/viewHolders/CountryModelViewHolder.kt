package com.energolabs.evergo.modules.country.viewHolders

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.country.CountrySelector
import com.energolabs.evergo.modules.country.models.CountryModel
import com.energolabs.evergo.modules.currencyWallet.controllers.CountryController
import com.energolabs.evergo.ui.viewHolders.BaseFlexibleViewHolder
import com.energolabs.evergo.utils.GlideUtils
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose Duque on 9/11/2015.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class CountryModelViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder<CountryModel>(
        view,
        activity,
        flexibleAdapter,
        false
), CountrySelector {

    private var tv_name: TextView? = null
    private var iv_flag: ImageView? = null

    override fun findViews(view: View) {
        super.findViews(view)
        tv_name = view.findViewById(R.id.tv_name) as TextView
        iv_flag = view.findViewById(R.id.iv_flag) as ImageView
    }

    override fun setListeners() {
        super.setListeners()
        rootView.setOnClickListener(this)
    }

    override fun updateView() {
        super.updateView()

        updateCountryName(
                item?.name
        )

        updateCountryFlag(
                item?.phoneCode,
                item?.flagUrl
        )
    }

    private fun updateCountryName(name: String?) {
        tv_name?.text = name
    }

    private fun updateCountryFlag(
            phoneCode: String?,
            flagUrl: String?
    ) {
        GlideUtils.loadImage(
                activity,
                iv_flag,
                flagUrl,
                CountryController.makeDefaultFlag(phoneCode)
        )
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            else -> {
                val data = Intent()
                data.putExtra(
                        CountrySelector.COUNTRY,
                        item
                )
                activity?.setResult(
                        CountrySelector.SELECT_COUNTRY_RESULT,
                        data
                )
                activity?.finish()
            }
        }
    }

}
