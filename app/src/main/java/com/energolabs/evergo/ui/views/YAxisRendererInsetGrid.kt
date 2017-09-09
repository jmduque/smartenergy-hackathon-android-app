package com.energolabs.evergo.ui.views

import android.graphics.Path

import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.renderer.YAxisRenderer
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.ViewPortHandler

/**
 * Created by Jose Duque on 2/27/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class YAxisRendererInsetGrid(
        viewPortHandler: ViewPortHandler,
        yAxis: YAxis,
        trans: Transformer
) : YAxisRenderer(
        viewPortHandler,
        yAxis,
        trans
) {

    var inset = 0f

    override
            /**
             * Calculates the path for a grid line.

             * @param p
             * *
             * @param i
             * *
             * @param positions
             * *
             * @return
             */
    fun linePath(p: Path, i: Int, positions: FloatArray): Path {
        p.moveTo(mViewPortHandler.offsetLeft() + inset, positions[i + 1])
        p.lineTo(mViewPortHandler.contentRight() - inset, positions[i + 1])
        return p
    }

}
