package com.energolabs.evergo.modules.user.validation.requests

import android.content.Context
import android.support.v4.app.Fragment

import com.energolabs.evergo.modules.media.requests.ImageUploadRequest
import com.jaiky.imagespickers.ImageSelector

/**
 * Created by Jose Duque on 12/8/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class IdPhotoUploaderFragmentController(
        private val fragment: Fragment,
        idCardUploaderListener: IdPhotoUploaderController.IdCardUploaderListener
) : IdPhotoUploaderController(
        idCardUploaderListener
), ImageUploadRequest.ImageUploadRequestListener {

    override val context: Context
        get() = fragment.context

    override fun pickPhoto() {
        ImageSelector.open(
                fragment,
                makeImageConfig()
        )
    }

}
