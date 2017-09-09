package com.energolabs.evergo.utils

import android.app.Activity
import android.support.annotation.StringRes
import android.widget.TextView
import java.util.*

/**
 * Created by Jose Duque on 2/20/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

object TextViewAnimUtils {

    private class TargetValue {
        internal var currentValue: Double = 0.toDouble()
    }

    private val updateSpeed = 0.04

    fun updateNumberValue(
            activity: Activity,
            textView: TextView?,
            initialValue: Double,
            finalValue: Double,
            @StringRes formatId: Int
    ) {
        if (finalValue <= 0) {
            textView?.text = activity.getString(
                    formatId,
                    0f
            ) ?: return
        }

        val targetValue = TargetValue()
        targetValue.currentValue = initialValue

        val tempo = (1000f / 60f).toLong()
        val timer = Timer()
        timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        var increase = (finalValue - targetValue.currentValue) * updateSpeed
                        increase = Math.max(increase, 0.001)
                        targetValue.currentValue += increase
                        if (targetValue.currentValue >= finalValue) {
                            targetValue.currentValue = finalValue
                            this.cancel()
                        }

                        activity.runOnUiThread {
                            textView?.text = activity.getString(
                                    formatId,
                                    targetValue.currentValue
                            )
                        }
                    }
                },
                tempo,
                tempo
        )
    }

}
