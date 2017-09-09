package com.energolabs.evergo.controllers

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.View

import com.energolabs.evergo.R

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE

/**
 * Created by Jose Duque on 12/23/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

abstract class PermissionsController(
        protected var activity: Activity,
        private val baseView: View
) {

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected abstract fun shouldShowRequestPermissionRationale(
            permission: String
    ): Boolean

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected abstract fun requestPermissions(
            permissions: Array<String>,
            permissionRequest: Int
    )

    private fun mayUse(
            permission: String,
            @StringRes checkMessage: Int,
            permissionRequest: Int
    ): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            return true
        }

        if (shouldShowRequestPermissionRationale(permission)) {
            Snackbar.make(
                    baseView,
                    checkMessage,
                    Snackbar.LENGTH_INDEFINITE
            ).setAction(
                    android.R.string.ok
            ) {
                requestPermissions(
                        arrayOf(permission),
                        permissionRequest
                )
            }
        } else {
            requestPermissions(
                    arrayOf(permission),
                    permissionRequest
            )
        }

        return false
    }

    fun mayUseCamera(permissionRequest: Int): Boolean {
        return mayUse(
                CAMERA,
                R.string.energo_check_camera_permissions,
                permissionRequest
        )
    }

    fun mayReadExternalStorage(permissionRequest: Int): Boolean {
        return mayUse(
                READ_EXTERNAL_STORAGE,
                R.string.energo_check_read_external_storage_permissions,
                permissionRequest
        )
    }

    fun mayWriteExternalStorage(permissionRequest: Int): Boolean {
        return mayUse(
                WRITE_EXTERNAL_STORAGE,
                R.string.energo_check_write_external_storage_permissions,
                permissionRequest
        )
    }

}
