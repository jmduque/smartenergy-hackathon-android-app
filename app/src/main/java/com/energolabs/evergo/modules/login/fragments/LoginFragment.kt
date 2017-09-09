package com.energolabs.evergo.modules.login.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.auth.models.AuthModel
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.country.CountrySelector
import com.energolabs.evergo.modules.country.fragments.CountrySelectorFragment
import com.energolabs.evergo.modules.country.models.CountryModel
import com.energolabs.evergo.modules.currencyWallet.controllers.CountryController
import com.energolabs.evergo.modules.login.requests.LoginByPhoneRequest
import com.energolabs.evergo.modules.main.activities.MainActivity
import com.energolabs.evergo.modules.password.fragments.ForgotPasswordFragment
import com.energolabs.evergo.modules.register.fragments.RegisterFragment
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.activities.DetailActivityNoToolbar
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.energolabs.evergo.utils.GlideUtils

import retrofit2.Call
import retrofit2.Response

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class LoginFragment : BaseFragment(),
        View.OnClickListener,
        LoginByPhoneRequest.LoginRequestListener {

    // INFO VIEWS
    private var iv_country: ImageView? = null
    private var et_phone: TextView? = null
    private var et_password: TextView? = null

    // ACTION VIEWS
    private var tv_forgot_password: View? = null
    private var tv_sign_in: View? = null
    private var tv_sign_up: View? = null

    // ERROR VIEWS
    private var tv_login_error: TextView? = null
    private var tv_phone_error: TextView? = null
    private var tv_password_error: TextView? = null

    // DATA
    private var countryModel: CountryModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        countryModel = CountryController.makeDefaultCountry()
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_login

    override fun findViews(view: View) {
        iv_country = view.findViewById(R.id.iv_country) as ImageView
        et_phone = view.findViewById(R.id.et_phone) as TextView
        et_password = view.findViewById(R.id.et_password) as TextView

        tv_forgot_password = view.findViewById(R.id.tv_forgot_password)
        tv_sign_in = view.findViewById(R.id.tv_sign_in)
        tv_sign_up = view.findViewById(R.id.tv_sign_up)

        tv_login_error = view.findViewById(R.id.tv_login_error) as TextView
        tv_phone_error = view.findViewById(R.id.tv_phone_error) as TextView
        tv_password_error = view.findViewById(R.id.tv_password_error) as TextView
    }

    override fun setListeners() {
        super.setListeners()
        iv_country?.setOnClickListener(this)
        tv_forgot_password?.setOnClickListener(this)
        tv_sign_in?.setOnClickListener(this)
        tv_sign_up?.setOnClickListener(this)
    }

    override fun disableViews() {
        iv_country?.isEnabled = false
        et_phone?.isEnabled = false
        et_password?.isEnabled = false
        tv_forgot_password?.isEnabled = false
        tv_sign_in?.isEnabled = false
        tv_sign_up?.isEnabled = false
    }

    override fun enableViews() {
        iv_country?.isEnabled = true
        et_phone?.isEnabled = true
        et_password?.isEnabled = true
        tv_forgot_password?.isEnabled = true
        tv_sign_in?.isEnabled = true
        tv_sign_up?.isEnabled = true
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
            R.id.tv_forgot_password -> {
                DetailActivityNoToolbar.openWithFragment(
                        context,
                        ForgotPasswordFragment::class.java.name,
                        ForgotPasswordFragment.makeArguments(),
                        true
                )
            }
            R.id.tv_sign_in -> {
                requestLogin()
            }
            R.id.tv_sign_up -> {
                DetailActivityNoToolbar.openWithFragmentForResult(
                        this,
                        context,
                        RegisterFragment.REGISTER,
                        RegisterFragment::class.java.name,
                        RegisterFragment.makeArguments(),
                        true
                )
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
            RegisterFragment.REGISTER -> {
                handleOnRegisterResult(
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

    /**
     * Handles the result of the registration action.

     * @param resultCode Activity result code
     * *
     * @param data       Activity result data
     */
    private fun handleOnRegisterResult(
            resultCode: Int,
            data: Intent?
    ) {
        when (resultCode) {
            RegisterFragment.REGISTER_OK -> {
                // Close the activity to avoid navigation-issues
                activity?.finish()
            }
        }
    }

    private fun updateCountryModel(countryModel: CountryModel) {
        this.countryModel = countryModel
        GlideUtils.loadImage(
                this,
                iv_country,
                countryModel.flagUrl,
                CountryController.makeDefaultFlag(
                        countryModel.phoneCode
                )
        )
    }

    private fun requestLogin() {
        tv_login_error?.text = null
        tv_phone_error?.text = null
        tv_password_error?.text = null

        var phone = et_phone?.text.toString()
        if (!Patterns.PHONE.matcher(phone).matches()) {
            tv_phone_error?.setText(R.string.energo_phone_error)
            return
        }
        phone = (countryModel?.phoneCode ?: "") + phone

        val password = et_password?.text.toString()
        if (TextUtils.isEmpty(password)) {
            tv_password_error?.setText(R.string.energo_password_error)
            return
        }

        LoginByPhoneRequest
                .Builder(context ?: return)
                .setPhoneNumber(phone)
                .setPassword(password)
                .setLoginRequestListener(this)
                .request()
    }

    override fun onLoginByPhoneError(
            call: Call<AuthModel>,
            response: Response<AuthModel>?,
            t: Throwable?
    ) {
        hideWaitDialog()
        tv_login_error?.setText(R.string.energo_login_error)
    }

    override fun onLoginByPhoneSuccess(
            call: Call<AuthModel>,
            response: Response<AuthModel>?
    ) {
        hideWaitDialog()
        val authModel = response?.body()
        if (TextUtils.isEmpty(authModel?.accessToken)) {
            onLoginByPhoneError(
                    call,
                    response, null
            )
            return
        }
        AuthPreferences(context ?: return).saveAuthModel(
                authModel
        )
        startActivity(
                MainActivity.makeIntent(context ?: return)
        )
        activity?.finish()
    }

    companion object {
        fun makeArguments(): Bundle? {
            return null
        }
    }
}
