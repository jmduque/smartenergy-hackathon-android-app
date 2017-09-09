package com.energolabs.evergo.controllers

import android.app.Activity
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View

/**
 * Created by Jose Duque on 12/23/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class ActivityPermissionsController(
        activity: Activity,
        baseView: View
) : PermissionsController(activity, baseView) {

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun shouldShowRequestPermissionRationale(
            permission: String
    ): Boolean {
        return activity.shouldShowRequestPermissionRationale(
                permission
        )
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun requestPermissions(
            permissions: Array<String>,
            permissionRequest: Int
    ) {
        activity.requestPermissions(
                permissions,
                permissionRequest
        )
    }

}
