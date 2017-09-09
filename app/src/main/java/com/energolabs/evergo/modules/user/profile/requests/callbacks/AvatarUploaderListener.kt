package com.energolabs.evergo.modules.user.profile.requests.callbacks

/**
 * Created by Jose Duque on 5/26/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

interface AvatarUploaderListener {

    fun onAvatarUploadedSuccess(url: String)

    fun onAvatarUploadError()

}
