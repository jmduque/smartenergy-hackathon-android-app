package com.energolabs.evergo.modules.password.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.auth.models.AuthModel
import com.energolabs.evergo.modules.auth.models.RequestPhoneCodeResponse
import com.energolabs.evergo.modules.auth.requests.RequestPhoneCodeRequest
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.country.CountrySelector
import com.energolabs.evergo.modules.country.fragments.CountrySelectorFragment
import com.energolabs.evergo.modules.country.models.CountryModel
import com.energolabs.evergo.modules.currencyWallet.controllers.CountryController
import com.energolabs.evergo.modules.main.activities.MainActivity
import com.energolabs.evergo.modules.password.requests.ForgotPasswordByPhoneRequest
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.energolabs.evergo.utils.GlideUtils

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class ForgotPasswordFragment : BaseFragment(), View.OnClickListener {

    // INPUT VIEWS
    private var iv_country: ImageView? = null
    private var et_phone: TextView? = null
    private var et_phone_code: TextView? = null
    private var et_password: TextView? = null
    private var et_password_repeat: TextView? = null

    // ERROR VIEWS
    private var tv_error: TextView? = null
    private var tv_phone_error: TextView? = null
    private var tv_phone_code_error: TextView? = null
    private var tv_password_error: TextView? = null

    // ACTION VIEWS
    private var tv_get_phone_code: TextView? = null
    private var tv_submit: View? = null

    // DATA
    private var countryModel: CountryModel? = null
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        countryModel = CountryController.makeDefaultCountry()
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_forgot_password

    override fun findViews(view: View) {
        // DATA VIEWS
        iv_country = view.findViewById(R.id.iv_country) as ImageView
        et_phone = view.findViewById(R.id.et_phone) as TextView
        et_phone_code = view.findViewById(R.id.et_phone_code) as TextView
        et_password = view.findViewById(R.id.et_password) as TextView
        et_password_repeat = view.findViewById(R.id.et_password_repeat) as TextView

        // ACTION VIEWS
        tv_get_phone_code = view.findViewById(R.id.tv_get_phone_code) as TextView
        tv_submit = view.findViewById(R.id.tv_submit)

        // ERROR VIEWS
        tv_error = view.findViewById(R.id.tv_error) as TextView
        tv_phone_error = view.findViewById(R.id.tv_phone_error) as TextView
        tv_phone_code_error = view.findViewById(R.id.tv_phone_code_error) as TextView
        tv_password_error = view.findViewById(R.id.tv_password_error) as TextView
    }

    override fun setListeners() {
        super.setListeners()
        iv_country?.setOnClickListener(this)
        tv_get_phone_code?.setOnClickListener(this)
        tv_submit?.setOnClickListener(this)
    }

    override fun disableViews() {
        iv_country?.isEnabled = false
        et_phone?.isEnabled = false
        et_password?.isEnabled = false
        tv_submit?.isEnabled = false
    }

    override fun enableViews() {
        iv_country?.isEnabled = true
        et_phone?.isEnabled = true
        et_password?.isEnabled = true
        tv_submit?.isEnabled = true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_country -> {
                DetailActivityNoCollapsing.openWithFragmentForResult(
                        this,
                        context,
                        CountrySelector.SELECT_COUNTRY,
                        CountrySelectorFragment::class.java.name,
                        CountrySelectorFragment.makeArguments(),
                        true
                )
            }
            R.id.tv_get_phone_code -> {
                requestPhoneCode()
            }
            R.id.tv_submit -> {
                submitRequest()
            }
        }
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        when (requestCode) {
            CountrySelector.SELECT_COUNTRY -> {
                handleOnCountrySelected(
                        resultCode,
                        data
                )
            }
            else -> {
                super.onActivityResult(
                        requestCode,
                        resultCode,
                        data
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateCountryModel(countryModel ?: return)
    }

    private fun handleOnCountrySelected(
            resultCode: Int,
            data: Intent?
    ) {
        when (resultCode) {
            CountrySelector.SELECT_COUNTRY_RESULT -> {
                updateCountryModel(
                        data?.getSerializableExtra(CountrySelector.COUNTRY) as CountryModel
                )
            }
            CountrySelector.SELECT_COUNTRY_CANCEL -> {
            }
        }
    }

    private fun updateCountryModel(countryModel: CountryModel?) {
        this.countryModel = countryModel
        GlideUtils.loadImage(
                this,
                iv_country,
                countryModel?.flagUrl,
                CountryController.makeDefaultFlag(
                        countryModel?.phoneCode
                )
        )
    }

    private fun requestPhoneCode() {
        var phone = et_phone?.text.toString()
        if (!Patterns.PHONE.matcher(phone).matches()) {
            return
        }
        phone = countryModel?.phoneCode?.plus(phone) ?: ""

        RequestPhoneCodeRequest.Builder(context ?: return)
                .setPhoneNumber(phone)
                .setType("forgot_password")
                .setResultListener(requestPhoneCodeRequestResultListener)
                .request()

        startPhoneCodeCountDownTimer()
    }

    private fun startPhoneCodeCountDownTimer() {
        tv_get_phone_code?.isEnabled = false
        countDownTimer = object : CountDownTimer(
                60000,
                1000
        ) {
            override fun onTick(time: Long) {
                tv_get_phone_code?.text = baseActivity?.getString(
                        R.string.energo_phone_code_time_count_down,
                        time / 1000
                )
            }

            override fun onFinish() {
                finishPhoneCodeCountDownTimer()
            }
        }
        countDownTimer?.start()
    }

    private fun finishPhoneCodeCountDownTimer() {
        tv_get_phone_code?.isEnabled = true
        tv_get_phone_code?.setText(R.string.energo_phone_code_get_code)
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
    }

    private val requestPhoneCodeRequestResultListener = object : RequestPhoneCodeRequest.ResultListener {
        override fun onResultSuccess(
                tag: Any?,
                response: RequestPhoneCodeResponse?
        ) {
        }

        override fun onResultError(
                tag: Any?,
                error: String?,
                errorCode: Int
        ) {
            finishPhoneCodeCountDownTimer()
            if (TextUtils.isEmpty(error)) {
                tv_phone_error?.setText(R.string.energo_phone_error)
            } else {
                tv_phone_error?.text = error
            }
        }
    }

    private fun submitRequest() {
        var phone = et_phone?.text.toString()
        if (!Patterns.PHONE.matcher(phone).matches()) {
            return
        }
        phone = countryModel?.phoneCode?.plus(phone) ?: ""

        val phoneCode = et_phone_code?.text.toString()
        if (TextUtils.isEmpty(phoneCode)) {
            tv_phone_code_error?.setText(R.string.energo_phone_code_error)
            return
        }

        val password = et_password?.text.toString()
        val passwordRepeat = et_password_repeat?.text.toString()
        if (!TextUtils.equals(password, passwordRepeat)) {
            tv_password_error?.setText(R.string.energo_passwords_dont_match_error)
            return
        }

        ForgotPasswordByPhoneRequest
                .Builder(context ?: return)
                .setPhoneNumber(phone)
                .setPhoneCode(phoneCode)
                .setNewPassword(password)
                .setResultListener(forgotPasswordByPhoneResultListener)
                .request()
    }

    private val forgotPasswordByPhoneResultListener = object : ForgotPasswordByPhoneRequest.ForgotPasswordByPhoneResultListener {
        override fun onResultSuccess(
                tag: Any?,
                response: AuthModel?
        ) {
            hideWaitDialog()
            AuthPreferences(context ?: return).saveAuthModel(
                    response
            )
            startActivity(
                    MainActivity.makeIntent(context ?: return)
            )
            activity?.finish()
        }

        override fun onResultError(
                tag: Any?,
                error: String?,
                errorCode: Int
        ) {
            hideWaitDialog()
            tv_error?.text = error
        }
    }

    companion object {

        fun makeArguments(): Bundle? {
            return null
        }
    }

}