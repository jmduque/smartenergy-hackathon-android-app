package com.energolabs.evergo.modules.loading.activities

import android.os.Bundle

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.auth.models.AuthModel
import com.energolabs.evergo.modules.auth.requests.AuthRefreshRequest
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.login.fragments.LoginFragment
import com.energolabs.evergo.modules.main.activities.MainActivity
import com.energolabs.evergo.ui.activities.BaseActivity
import com.energolabs.evergo.ui.activities.DetailActivityNoToolbar

/**
 * Created by Jose Duque on 12/2/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class LoadingActivity : BaseActivity(),
        AuthRefreshRequest.ResultListener {

    private var authPreferences: AuthPreferences? = null

    override val layoutId: Int
        get() = R.layout.activity_loading

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authPreferences = AuthPreferences(this)
        refreshAuth()
    }

    private fun refreshAuth() {
        val authModel = authPreferences?.authModel
        AuthRefreshRequest.Builder(this)
                .setRequest(authModel)
                .setResultListener(this)
                .request()
    }

    override fun onResultSuccess(
            tag: Any?,
            response: AuthModel?
    ) {
        authPreferences?.saveAuthModel(
                response
        )
        startActivity(
                MainActivity.makeIntent(this)
        )
    }

    override fun onResultError(
            tag: Any?,
            error: String?,
            errorCode: Int
    ) {
        authPreferences?.saveAuthModel(
                null
        )
        DetailActivityNoToolbar.openWithFragment(
                this,
                LoginFragment::class.java.name,
                LoginFragment.makeArguments(),
                true
        )
    }
}
