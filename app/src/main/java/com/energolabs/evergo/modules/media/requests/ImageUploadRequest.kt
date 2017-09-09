package com.energolabs.evergo.modules.media.requests

import android.content.Context
import android.graphics.Bitmap

import com.energolabs.evergo.modules.media.models.Image
import com.energolabs.evergo.modules.media.models.ImageUploadResult
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilderWithBody
import com.energolabs.evergo.utils.BitmapUtils

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface ImageUploadRequest {

    @POST("files/upload")
    fun upload(
            @Body image: Image
    ): Call<ImageUploadResult>

    class Builder(
            context: Context
    ) : RequestBuilderWithBody<Image, ImageUploadResult>(
            context
    ) {

        override val request = Image()
        private var imageUploadRequestListener: ImageUploadRequestListener? = null

        fun setImageUploadRequestListener(imageUploadRequestListener: ImageUploadRequestListener): Builder {
            this.imageUploadRequestListener = imageUploadRequestListener
            return this
        }

        override fun setRequest(request: Image?): Builder {
            if (request !=
                    null) {
                this.request.image = request.image
            }
            return this
        }

        /**
         * @param bitmap Bitmap of the image to be uploaded
         */
        fun bitmap(bitmap: Bitmap): Builder {
            base64(
                    BitmapUtils.bitmapToBase64(bitmap)
            )
            return this
        }

        /**
         * @param path Local pathSquaredAndResize to a valid jpeg/png
         */
        fun path(
                path: String
        ): Builder {
            val bm = BitmapUtils.decodeBitmapFromPath(
                    path
            )
            bitmap(bm)
            bm.recycle()
            return this
        }

        /**
         * @param path Local pathSquaredAndResize to a valid jpeg/png
         */
        fun pathSquaredAndResize(
                path: String,
                maxDim: Int
        ): Builder {
            val bm = BitmapUtils.decodeSquaredBitmapFromPath(
                    path,
                    maxDim
            )
            bitmap(bm ?: return this)
            bm.recycle()
            return this
        }

        /**
         * @param base64Data base64 encoded picture data
         */
        fun base64(base64Data: String?): Builder {
            request.image = "data:image/jpeg;base64," + base64Data
            return this
        }

        override fun request() {
            val imageUploadRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    ImageUploadRequest::class.java
            )

            val call = imageUploadRequest.upload(
                    request
            )

            try {
                call.enqueue(this)
            } catch (ignored: Exception) {
                imageUploadRequestListener?.onImageUploadError(
                        call,
                        null,
                        null
                )
            }

        }

        override fun onResponse(
                call: Call<ImageUploadResult>,
                response: Response<ImageUploadResult>?
        ) {
            if (response?.isSuccessful ?: false) {
                imageUploadRequestListener?.onImageUploadSuccess(
                        call,
                        response
                )
            } else {
                imageUploadRequestListener?.onImageUploadError(
                        call,
                        response,
                        null
                )
            }
        }

        override fun onFailure(
                call: Call<ImageUploadResult>,
                t: Throwable
        ) {
            imageUploadRequestListener?.onImageUploadError(
                    call,
                    null,
                    t
            )
        }
    }

    interface ImageUploadRequestListener {

        fun onImageUploadError(
                call: Call<ImageUploadResult>,
                response: Response<ImageUploadResult>?,
                t: Throwable?
        )

        fun onImageUploadSuccess(
                call: Call<ImageUploadResult>,
                response: Response<ImageUploadResult>?
        )

    }

}