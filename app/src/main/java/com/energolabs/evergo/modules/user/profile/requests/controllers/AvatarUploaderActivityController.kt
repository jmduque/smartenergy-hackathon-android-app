package com.energolabs.evergo.modules.user.profile.requests.controllers

import android.app.Activity
import android.content.Context

import com.energolabs.evergo.modules.media.requests.ImageUploadRequest
import com.energolabs.evergo.modules.user.profile.requests.callbacks.AvatarUploaderListener
import com.jaiky.imagespickers.ImageSelector

/**
 * Created by Jose Duque on 12/8/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class AvatarUploaderActivityController(
        private val activity: Activity,
        avatarUploaderListener: AvatarUploaderListener
) : AvatarUploaderController(
        avatarUploaderListener
), ImageUploadRequest.ImageUploadRequestListener {

    override val context: Context
        get() = activity

    override fun pickAvatar() {
        ImageSelector.open(
                activity,
                makeImageConfig()
        )
    }

}
