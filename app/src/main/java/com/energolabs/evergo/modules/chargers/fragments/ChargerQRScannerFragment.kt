package com.energolabs.evergo.modules.chargers.fragments

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.devices.fragments.SmartMeterBindingNameFragment
import com.energolabs.evergo.modules.devices.models.DeviceInfo
import com.energolabs.evergo.modules.devices.requests.GetDeviceFromQRRequest
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

/**
 * Created by Jose Duque on 2/7/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class ChargerQRScannerFragment : BaseFragment(),
        ZXingScannerView.ResultHandler,
        BaseResultListener<DeviceInfo>,
        View.OnClickListener {

    private var mScannerView: ZXingScannerView? = null

    private var iv_manual_input: View? = null

    override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this)
        mScannerView?.startCamera()
        setTitle(R.string.energo_smart_meter_binding_title)
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

    override val layoutId: Int
        get() = R.layout.fragment_charger_qr_code_scanner

    override fun findViews(view: View) {
        mScannerView = view.findViewById(R.id.scanner) as ZXingScannerView
        iv_manual_input = view.findViewById(R.id.iv_manual_input)
    }

    override fun setListeners() {
        super.setListeners()
        iv_manual_input?.setOnClickListener(this)
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_manual_input -> {
                DetailActivityNoCollapsing.openWithFragment(
                        context,
                        ChargerStartFragment::class.java.name,
                        ChargerStartFragment.makeArguments(),
                        true
                )
                activity.finish()
            }
        }
    }

    override fun handleResult(rawResult: Result) {
        Toast.makeText(
                activity,
                "Contents = " + rawResult.text + ", Format = " + rawResult.barcodeFormat.toString(),
                Toast.LENGTH_SHORT
        ).show()

        getFromURL(rawResult.text)

    }

    private fun getFromURL(url: String) {
        GetDeviceFromQRRequest.Builder(context ?: return)
                .setUrl(url)
                .setResultListener(this)
                .request()
        showWaitDialog()
    }

    override fun onResultSuccess(
            tag: Any?,
            response: DeviceInfo?
    ) {
        hideWaitDialog()
        DetailActivityNoCollapsing.openWithFragment(
                context,
                SmartMeterBindingNameFragment::class.java.name,
                SmartMeterBindingNameFragment.makeArguments(
                        response
                ),
                true
        )
        activity?.finish()
    }

    override fun onResultError(
            tag: Any?,
            error: String?,
            errorCode: Int
    ) {
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        val handler = Handler()
        handler.postDelayed({
            hideWaitDialog()
            mScannerView?.resumeCameraPreview(this@ChargerQRScannerFragment)
        }, 2000)
    }

    companion object {
        fun makeArguments(): Bundle? {
            return null
        }
    }

}