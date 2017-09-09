package com.energolabs.evergo.ui.views

import android.content.Context
import android.util.AttributeSet

import com.github.mikephil.charting.charts.LineChart

/**
 * Created by Jose Duque on 12/22/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class EnergoLineChart : LineChart {

    constructor(
            context: Context
    ) : super(context)

    constructor(
            context: Context,
            attrs:
            AttributeSet
    ) : super(
            context,
            attrs
    )

    constructor(
            context: Context,
            attrs: AttributeSet,
            defStyle: Int
    ) : super(
            context,
            attrs,
            defStyle
    )

    override fun init() {
        super.init()
        mRenderer = EnergoLineChartRenderer(this, mAnimator, mViewPortHandler)
    }

}
