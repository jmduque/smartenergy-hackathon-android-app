package com.energolabs.evergo.modules.user.validation.requests

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import com.energolabs.evergo.BuildConfig
import com.energolabs.evergo.modules.media.models.ImageUploadResult
import com.energolabs.evergo.modules.media.requests.ImageUploadRequest
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

abstract class IdPhotoUploaderController(
        private val idCardUploaderListener: IdPhotoUploaderController.IdCardUploaderListener
) : ImageUploadRequest.ImageUploadRequestListener {

    interface IdCardUploaderListener {

        fun onIdPhotoUploadedSuccess(url: String)

        fun onIdPhotoUploadError()

    }

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
                .crop(
                        16,
                        10,
                        640,
                        400
                )
                .build()
    }

    abstract fun pickPhoto()

    fun uploadIdCard(path: String) {
        ImageUploadRequest.Builder(context)
                .path(path)
                .setImageUploadRequestListener(this)
                .request()
    }

    fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            val pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT)
            for (path in pathList) {
                uploadIdCard(path)
            }
        }
    }

    override fun onImageUploadError(
            call: Call<ImageUploadResult>,
            response: Response<ImageUploadResult>?,
            t: Throwable?
    ) {
        idCardUploaderListener.onIdPhotoUploadError()
    }

    override fun onImageUploadSuccess(
            call: Call<ImageUploadResult>,
            response: Response<ImageUploadResult>?
    ) {
        idCardUploaderListener.onIdPhotoUploadedSuccess(
                response?.body()?.url ?: ""
        )
    }
}
