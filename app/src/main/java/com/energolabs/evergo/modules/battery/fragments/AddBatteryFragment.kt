package com.energolabs.evergo.modules.battery.fragments

import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.battery.models.BatteryModel
import com.energolabs.evergo.modules.battery.requests.PostBatteryRequest
import com.energolabs.evergo.modules.location.models.GeoLocationModel
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.ui.dialogs.TextInputDialog
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker


/**
 * Created by Jose Duque on 12/23/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class AddBatteryFragment : BaseFragment(),
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener,
        BaseResultListener<BatteryModel> {

    private var ll_name: View? = null
    private var ll_capacity: View? = null
    private var ll_type: View? = null
    private var ll_location: View? = null

    private var et_name: TextView? = null
    private var et_capacity: TextView? = null
    private var et_type: TextView? = null
    private var et_location: TextView? = null

    private var fab_add: View? = null

    private var name: String? = null
    private var capacity: Double? = null
    private var type: String? = null
    private var location: GeoLocationModel = GeoLocationModel()

    private val PLACE_PICKER_REQUEST = 1

    var googleApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleApiClient = GoogleApiClient.
                Builder(activity).
                addApi(Places.GEO_DATA_API).
                addApi(Places.PLACE_DETECTION_API).
                enableAutoManage(activity, this).
                build()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val layoutId: Int
        get() = R.layout.fragment_add_battery

    override fun findViews(view: View) {
        ll_name = view.findViewById(R.id.ll_name)
        ll_capacity = view.findViewById(R.id.ll_capacity)
        ll_type = view.findViewById(R.id.ll_type)
        ll_location = view.findViewById(R.id.ll_location)
        
        et_name = view.findViewById(R.id.et_name) as TextView
        et_capacity = view.findViewById(R.id.et_capacity) as TextView
        et_type = view.findViewById(R.id.et_type) as TextView
        et_location = view.findViewById(R.id.et_location) as TextView
        
        fab_add = view.findViewById(R.id.fab_add)
    }

    override fun setListeners() {
        super.setListeners()
        ll_name?.setOnClickListener(this)
        ll_capacity?.setOnClickListener(this)
        ll_type?.setOnClickListener(this)
        ll_location?.setOnClickListener(this)
        fab_add?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_add_battery_title)
    }

    private fun updateName(
            name: String?
    ) {
        this.name = name
        et_name?.text = name
    }

    private fun updateCapacity(
            name: String?
    ) {
        this.capacity = name?.toDouble()
        et_capacity?.text = getString(
                R.string.energo_energy_format,
                capacity
        )
    }

    private fun updateType(type: String?) {
        this.type = type
        et_type?.text = type
    }

    private fun updateLocation(
            name: String,
            latitude: Double,
            longitude: Double
    ) {
        location.name = name
        location.latitude = latitude
        location.longitude = longitude
        et_location?.text = name +
                '\n' +
                getString(R.string.energo_energy_format, latitude) +
                ", " +
                getString(R.string.energo_energy_format, longitude)
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ll_name -> {
                inputNewName(
                        name
                )
            }
            R.id.ll_capacity -> {
                inputCapacity(
                        capacity?.toString()
                )
            }
            R.id.ll_type -> {
                selectType(type)
            }
            R.id.ll_location -> {
                selectLocation()
            }
            R.id.fab_add -> {
                saveBattery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                val place = PlacePicker.getPlace(data, activity)
                updateLocation(
                        place.name.toString(),
                        place.latLng.latitude,
                        place.latLng.longitude
                )
            }
        }
    }

    private fun inputNewName(currentName: String?) {
        val textInputDialog = TextInputDialog()
        textInputDialog.showDialog(
                activity,
                (view as ViewGroup),
                R.string.energo_add_battery_name_tag,
                currentName ?: "",
                R.string.energo_user_profile_name_label,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    updateName(textInputDialog.getInput())
                }
        )
    }

    private fun inputCapacity(currentCapacity: String?) {
        val textInputDialog = TextInputDialog()
        textInputDialog.showDialog(
                activity,
                (view as ViewGroup),
                R.string.energo_add_battery_capacity_tag,
                currentCapacity ?: "",
                R.string.energo_add_battery_capacity_tag,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    updateCapacity(textInputDialog.getInput())
                },
                InputType.TYPE_NUMBER_FLAG_DECIMAL
        )
    }

    private fun indexOfType(type: String?): Int {
        return when (type) {
            "ev" -> {
                0
            }
            "pw" -> {
                1
            }
            else -> {
                -1
            }
        }
    }

    private fun selectType(type: String?) {
        MaterialDialog.
                Builder(context ?: return).
                title(R.string.energo_add_battery_type_tag).
                items(
                        getStringSafely(R.string.energo_add_battery_type_ev),
                        getStringSafely(R.string.energo_add_battery_type_pw)
                ).
                itemsCallbackSingleChoice(
                        indexOfType(type)
                ) { dialog, itemView, which, text ->
                    updateType(text?.toString())
                    true
                }.
                show()
    }

    private fun selectLocation() {
        val builder = PlacePicker.IntentBuilder()
        startActivityForResult(
                builder.build(activity),
                PLACE_PICKER_REQUEST
        )
    }

    private fun saveBattery() {
        showWaitDialog()
        val batteryModel = BatteryModel()
        batteryModel.name = name
        batteryModel.capacity = capacity
        batteryModel.location = location
        batteryModel.type = type
        PostBatteryRequest.
                Builder(activity).
                setRequest(batteryModel).
                setResultListener(this).
                request()
    }

    override fun onResultSuccess(tag: Any?, response: BatteryModel?) {
        hideWaitDialog()
        showMessageDialog(
                activity,
                getString(R.string.energo_add_battery_success)
        )
    }

    override fun onResultError(tag: Any?, error: String?, errorCode: Int) {
        hideWaitDialog()
        showMessageDialog(
                activity,
                error ?: getString(R.string.energo_add_battery_error)
        )
    }

    companion object {
        fun makeArguments(): Bundle? {
            return null
        }
    }

}