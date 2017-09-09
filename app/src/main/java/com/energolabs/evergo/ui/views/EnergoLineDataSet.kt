package com.energolabs.evergo.ui.views

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet

/**
 * Created by Jose Duque on 2/27/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class EnergoLineDataSet(yVals: List<Entry>, label: String) : LineDataSet(yVals, label) {
    override fun getEntryForIndex(index: Int): Entry? {
        if ((mValues?.size ?: return null) <= index) {
            return null
        }

        return mValues[index]
    }
}
