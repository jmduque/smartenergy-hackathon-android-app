package com.energolabs.evergo.controllers

import android.app.Activity
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View

import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 12/23/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class FragmentPermissionsController(
        private val baseFragment: BaseFragment,
        activity: Activity,
        baseView: View
) : PermissionsController(activity, baseView) {

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun shouldShowRequestPermissionRationale(
            permission: String
    ): Boolean {
        return baseFragment.shouldShowRequestPermissionRationale(
                permission
        )
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun requestPermissions(
            permissions: Array<String>,
            permissionRequest: Int
    ) {
        baseFragment.requestPermissions(
                permissions,
                permissionRequest
        )
    }

}
