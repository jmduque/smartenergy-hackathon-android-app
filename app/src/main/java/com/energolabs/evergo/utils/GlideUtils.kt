package com.energolabs.evergo.utils

import android.app.Activity
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

/**
 * Created by Jose Duque on 12/8/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

object GlideUtils {

    private fun loadImage(
            requestManager: RequestManager,
            imageView: ImageView?,
            url: String?,
            @DrawableRes defaultImage: Int
    ) {
        requestManager
                .load(url)
                .centerCrop()
                .placeholder(defaultImage)
                .crossFade()
                .into(imageView)
    }

    fun loadImage(
            context: Context?,
            imageView: ImageView?,
            url: String?,
            @DrawableRes defaultImage: Int
    ) {
        loadImage(
                Glide.with(context ?: return),
                imageView,
                url,
                defaultImage
        )
    }

    fun loadImage(
            activity: Activity?,
            imageView: ImageView?,
            url: String?,
            @DrawableRes defaultImage: Int
    ) {
        loadImage(
                Glide.with(activity ?: return),
                imageView,
                url,
                defaultImage
        )
    }

    fun loadImage(
            fragment: Fragment?,
            imageView: ImageView?,
            url: String?,
            @DrawableRes defaultImage: Int
    ) {
        loadImage(
                Glide.with(fragment ?: return),
                imageView,
                url,
                defaultImage
        )
    }

}
