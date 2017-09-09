package com.energolabs.evergo.utils

import android.content.Context
import android.widget.ImageView
import com.jaiky.imagespickers.ImageLoader

/**
 * Created by Jose Duque on 12/8/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class ImageLoader : ImageLoader{

    override fun displayImage(
            context: Context,
            path: String?,
            imageView: ImageView
    ) {
        GlideUtils.loadImage(
                context,
                imageView,
                path,
                com.jaiky.imagespickers.R.drawable.global_img_default
        )
    }

}
