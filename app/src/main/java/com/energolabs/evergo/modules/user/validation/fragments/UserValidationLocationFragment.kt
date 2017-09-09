package com.energolabs.evergo.modules.user.validation.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.location.LocationSelector
import com.energolabs.evergo.modules.location.fragments.LocationSelectorFragment
import com.energolabs.evergo.modules.location.models.LocationModel
import com.energolabs.evergo.modules.user.profile.models.UserModel
import com.energolabs.evergo.ui.activities.DetailActivity
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.energolabs.evergo.utils.ToastUtil
import java.util.*

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class UserValidationLocationFragment : BaseFragment(), View.OnClickListener {

    private var et_area: TextView? = null
    private var et_address: TextView? = null
    private var btn_next: View? = null

    private var userModel: UserModel? = null

    private val locations = ArrayList<LocationModel>()
    private var locationModel: LocationModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val args = arguments
        if (args != null) {
            userModel = args.getSerializable(USER) as UserModel
        }
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_user_validation_location

    override fun findViews(view: View) {
        et_area = view.findViewById(R.id.et_area) as TextView
        et_address = view.findViewById(R.id.et_address) as TextView
        btn_next = view.findViewById(R.id.btn_next)
    }

    override fun setListeners() {
        super.setListeners()
        et_area?.setOnClickListener(this)
        btn_next?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_user_validation_title)
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.et_area -> {
                locations.clear()
                locationModel = null
                requestLocation(null)
            }
            R.id.btn_next -> {
                onNextClicked()
            }
        }
    }

    private fun requestLocation(areaCode: Int?) {
        DetailActivity.openWithFragmentForResult(
                this,
                context,
                LocationSelector.SELECT_LOCATION,
                LocationSelectorFragment::class.java.name,
                LocationSelectorFragment.makeArguments(areaCode),
                true
        )
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        when (requestCode) {
            LocationSelector.SELECT_LOCATION -> {
                onLocationSelected(
                        resultCode,
                        data
                )
            }
        }
        super.onActivityResult(
                requestCode,
                resultCode,
                data
        )

    }

    private fun onLocationSelected(
            resultCode: Int,
            data: Intent?
    ) {
        when (resultCode) {
            LocationSelector.SELECT_LOCATION_RESULT -> {
                data ?: return
                val locationModel = data.getSerializableExtra(LocationSelector.LOCATION) as LocationModel

                locations.add(locationModel)
                val areaCode = locationModel.areaCode
                if (areaCode % 100 > 0) {
                    onLastLocationSelected()
                } else {
                    requestLocation(areaCode)
                }
            }
            LocationSelector.SELECT_LOCATION_CANCEL -> {
                locations.clear()
                et_area?.text = null
            }
        }
    }

    private fun onLastLocationSelected(): LocationModel? {
        locationModel = LocationModel()

        var name = ""
        for (i in locations.indices) {
            if (i == 0) {
                // FIRST ONE CASE
                name += (locations[i].areaName ?: "")
            } else {
                name += ", " + (locations[i].areaName ?: "")
            }
        }

        locationModel?.areaName = name
        locationModel?.areaCode = locations[locations.size - 1].areaCode

        et_area?.text = name

        return locationModel
    }

    private fun onNextClicked() {
        if (locationModel == null) {
            ToastUtil.showToastLong(
                    activity,
                    R.string.energo_smart_meter_binding_error_area
            )
            return
        }

        val address = et_address?.text.toString()
        if (TextUtils.isEmpty(address)) {
            ToastUtil.showToastLong(
                    activity,
                    R.string.energo_smart_meter_binding_error_address
            )
            return
        }

        userModel?.location = locationModel
        userModel?.address = address

        DetailActivityNoCollapsing.openWithFragment(
                context,
                UserValidationIdPhotoFragment::class.java.name,
                UserValidationIdPhotoFragment.makeArguments(
                        userModel ?: return
                ),
                true
        )
        activity?.finish()
    }

    companion object {

        private val USER = "USER"

        fun makeArguments(
                userModel: UserModel
        ): Bundle {
            val args = Bundle()
            args.putSerializable(
                    USER,
                    userModel
            )
            return args
        }
    }

}
