package com.energolabs.evergo.modules.devices.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.devices.models.DeviceInfo
import com.energolabs.evergo.modules.devices.requests.GetDeviceRequest
import com.energolabs.evergo.modules.devices.requests.PutDeviceInfoRequest
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.ui.dialogs.TextInputDialog
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.energolabs.evergo.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jose Duque on 12/23/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class DeviceProfileFragment : BaseFragment(), View.OnClickListener, BaseResultListener<DeviceInfo> {

    private var ll_name: View? = null
    private var tv_name: TextView? = null

    private var ll_attach_to: View? = null
    private var tv_attach_to: TextView? = null

    private var ll_status: View? = null
    private var tv_status: TextView? = null

    private var ll_access_time: View? = null
    private var tv_access_time: TextView? = null

    private var ll_model: View? = null
    private var tv_model: TextView? = null

    private var ll_version: View? = null
    private var tv_version: TextView? = null

    private var ll_uuid: View? = null
    private var tv_uuid: TextView? = null

    private var deviceInfo: DeviceInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val args = arguments
        if (args != null) {
            deviceInfo = args.getSerializable(DEVICE_INFO) as DeviceInfo
        }
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_device_profile

    override fun findViews(view: View) {
        ll_name = view.findViewById(R.id.ll_name)
        tv_name = view.findViewById(R.id.tv_name) as TextView

        ll_attach_to = view.findViewById(R.id.ll_attach_to)
        tv_attach_to = view.findViewById(R.id.tv_attach_to) as TextView

        ll_status = view.findViewById(R.id.ll_status)
        tv_status = view.findViewById(R.id.tv_status) as TextView

        ll_access_time = view.findViewById(R.id.ll_access_time)
        tv_access_time = view.findViewById(R.id.tv_access_time) as TextView

        ll_model = view.findViewById(R.id.ll_model)
        tv_model = view.findViewById(R.id.tv_model) as TextView

        ll_version = view.findViewById(R.id.ll_version)
        tv_version = view.findViewById(R.id.tv_version) as TextView

        ll_uuid = view.findViewById(R.id.ll_uuid)
        tv_uuid = view.findViewById(R.id.tv_uuid) as TextView

        updateProfile(deviceInfo)
    }

    override fun setListeners() {
        super.setListeners()
        ll_name?.setOnClickListener(this)
        ll_attach_to?.setOnClickListener(this)

        ll_status?.setOnClickListener(this)
        ll_access_time?.setOnClickListener(this)
        ll_model?.setOnClickListener(this)
        ll_version?.setOnClickListener(this)
        ll_uuid?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_smart_meter_profile_title)
        requestDeviceInfo()
    }

    private fun updateProfile(
            deviceInfo: DeviceInfo?
    ) {
        tv_name?.text = deviceInfo?.name
        val owner = deviceInfo?.owner
        val ownerName = owner?.name
        tv_attach_to?.text = ownerName
        if (!TextUtils.isEmpty(ownerName)) {
            setTitle(
                    baseActivity?.getString(
                            R.string.energo_smart_meter_profile_owned_title,
                            ownerName
                    )
            )
        }

        tv_status?.text = deviceInfo?.deviceStatus
        tv_access_time?.text = accesTimeFormat.format(
                DateUtils.getDateFromISO8601String(
                        deviceInfo?.accessTime
                )
        )
        tv_model?.text = deviceInfo?.model
        tv_version?.text = deviceInfo?.hwVersion
        tv_uuid?.text = deviceInfo?.uuid
    }

    private fun requestDeviceInfo() {
        GetDeviceRequest.Builder(context ?: return)
                .setId(
                        deviceInfo?._id
                )
                .setResultListener(this)
                .request()
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ll_name -> {
                inputNewName(
                        deviceInfo?.name
                )
            }
        }
    }

    override fun onResultSuccess(
            tag: Any?,
            response: DeviceInfo?
    ) {
        this.deviceInfo = response
        updateProfile(response)
    }

    override fun onResultError(
            tag: Any?,
            error: String?,
            errorCode: Int) {

    }

    private fun inputNewName(currentName: String?) {
        val textInputDialog = TextInputDialog()
        textInputDialog.showDialog(
                activity ?: return,
                (view as ViewGroup),
                R.string.energo_user_profile_name_label,
                currentName ?: "",
                R.string.energo_user_profile_name_label,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    val inputText = textInputDialog.getInput()
                    PutDeviceInfoRequest
                            .Builder(context)
                            .setId(deviceInfo?._id)
                            .setName(inputText)
                            .setResultListener(this@DeviceProfileFragment)
                            .request()
                }
        )
    }

    companion object {

        private val DEVICE_INFO = "device_info"

        private val accesTimeFormat = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.ENGLISH
        )

        fun makeArguments(
                deviceInfo: DeviceInfo?
        ): Bundle {
            val args = Bundle()
            args.putSerializable(
                    DEVICE_INFO,
                    deviceInfo
            )
            return args
        }
    }

}
