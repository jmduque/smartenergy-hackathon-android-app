package com.energolabs.evergo.modules.user.profile.requests.controllers

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import com.energolabs.evergo.BuildConfig
import com.energolabs.evergo.modules.media.models.ImageUploadResult
import com.energolabs.evergo.modules.media.requests.ImageUploadRequest
import com.energolabs.evergo.modules.user.profile.requests.callbacks.AvatarUploaderListener
import com.energolabs.evergo.utils.ImageLoader
import com.jaiky.imagespickers.ImageConfig
import com.jaiky.imagespickers.ImageSelector
import com.jaiky.imagespickers.ImageSelectorActivity
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Jose Duque on 12/8/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

abstract class AvatarUploaderController(
        private val avatarUploaderListener: AvatarUploaderListener
) : ImageUploadRequest.ImageUploadRequestListener {

    abstract val context: Context

    fun makeImageConfig(): ImageConfig {
        val resources = context.resources
        return ImageConfig
                .Builder(ImageLoader())
                .steepToolBarColor(resources.getColor(android.R.color.black))
                .titleBgColor(resources.getColor(android.R.color.black))
                .titleSubmitTextColor(resources.getColor(android.R.color.white))
                .titleTextColor(resources.getColor(android.R.color.white))
                .singleSelect()
                .showCamera()
                .filePath("/temp/" + BuildConfig.APPLICATION_ID)
                .crop()
                .build()
    }

    abstract fun pickAvatar()

    fun uploadAvatar(path: String) {
        ImageUploadRequest
                .Builder(context)
                .pathSquaredAndResize(path, 256)
                .setImageUploadRequestListener(this)
                .request()
    }

    fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        data ?: return
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE
                && resultCode == RESULT_OK) {
            val pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT)
            for (path in pathList) {
                uploadAvatar(path)
            }
        }
    }

    override fun onImageUploadError(
            call: Call<ImageUploadResult>,
            response: Response<ImageUploadResult>?,
            t: Throwable?
    ) {
        avatarUploaderListener.onAvatarUploadError()
    }

    override fun onImageUploadSuccess(
            call: Call<ImageUploadResult>,
            response: Response<ImageUploadResult>?
    ) {
        avatarUploaderListener.onAvatarUploadedSuccess(
                response?.body()?.url ?: ""
        )
    }
}
