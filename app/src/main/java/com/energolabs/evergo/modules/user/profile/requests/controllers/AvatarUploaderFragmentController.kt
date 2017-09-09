package com.energolabs.evergo.modules.user.profile.requests.controllers

import android.content.Context
import android.support.v4.app.Fragment

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

class AvatarUploaderFragmentController(
        private val fragment: Fragment,
        avatarUploaderListener: AvatarUploaderListener
) : AvatarUploaderController(avatarUploaderListener),
        ImageUploadRequest.ImageUploadRequestListener {

    override val context: Context
        get() = fragment.context

    override fun pickAvatar() {
        ImageSelector.open(
                fragment,
                makeImageConfig()
        )
    }

}
