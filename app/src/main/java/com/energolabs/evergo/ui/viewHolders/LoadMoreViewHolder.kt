package com.energolabs.evergo.ui.viewHolders


import android.app.Activity
import android.view.View
import com.energolabs.evergo.models.BaseModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 8/30/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LoadMoreViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder<BaseModel>(
        view,
        activity,
        flexibleAdapter,
        false
), View.OnClickListener
