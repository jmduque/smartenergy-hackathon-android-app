package com.energolabs.evergo.utils

import android.content.Context
import android.os.Looper
import android.support.annotation.StringRes
import android.widget.Toast

object ToastUtil {

    private var toast: Toast? = null

    fun showToastShort(
            context: Context?,
            text: String?
    ) {
        showToastShort(
                context,
                toast,
                text
        )
    }

    private fun showToastShort(
            context: Context?,
            toast: Toast?,
            text: String?
    ) {
        if (toast != null) {
            toast.setText(text)
            toast.show()
        } else {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }

            this.toast = Toast.makeText(
                    context ?: return,
                    text,
                    Toast.LENGTH_SHORT
            )
            this.toast?.show()
        }
    }

    fun showToastShort(
            context: Context?,
            @StringRes resId: Int
    ) {
        showToastShort(context, toast, resId)
    }

    private fun showToastShort(
            context: Context?,
            toast: Toast?,
            @StringRes resId: Int
    ) {
        if (toast != null) {
            toast.setText(resId)
            toast.show()
        } else {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }

            this.toast = Toast.makeText(
                    context ?: return,
                    resId,
                    Toast.LENGTH_SHORT
            )
            this.toast?.show()
        }
    }

    fun showToastLong(
            context: Context?,
            text: String?
    ) {
        showToastLong(context, toast, text)
    }

    private fun showToastLong(
            context: Context?,
            toast: Toast?,
            text: String?
    ) {
        if (toast != null) {
            toast.setText(text)
            toast.show()
        } else {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }

            this.toast = Toast.makeText(
                    context ?: return,
                    text,
                    Toast.LENGTH_LONG
            )
            this.toast?.show()
        }
    }

    fun showToastLong(
            context: Context?,
            @StringRes resId: Int
    ) {
        showToastLong(context, toast, resId)
    }

    private fun showToastLong(
            context: Context?,
            toast: Toast?,
            @StringRes resId: Int
    ) {
        if (toast != null) {
            toast.setText(resId)
            toast.show()
        } else {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }

            this.toast = Toast.makeText(
                    context ?: return,
                    resId,
                    Toast.LENGTH_LONG
            )
            this.toast?.show()
        }
    }
}
