package com.energolabs.evergo.controllers

import android.content.Context
import android.support.annotation.StringRes
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.energolabs.evergo.utils.BitmapUtils

/**
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
object ViewsController {

    /**
     * @param view The target [View]
     */
    fun hideView(view: View) {
        setVisibility(view, View.GONE)
    }

    /**
     * @param view The target [View]
     */
    fun setViewInvisible(view: View) {
        setVisibility(view, View.INVISIBLE)
    }

    /**
     * @param view The target [View]
     */
    fun showView(view: View?) {
        setVisibility(view, View.VISIBLE)
    }

    /**
     * @param view The target [View]
     * *
     * @return view's width value on pixels in the screen
     */
    fun getWidth(view: View?): Int {
        view?.measure(0, 0)
        return view?.measuredWidth ?: 0

    }

    /**
     * @param view The target [View]
     * *
     * @return view's height value on pixels in the screen
     */
    fun getHeight(view: View?): Int {
        view?.measure(0, 0)
        return view?.measuredHeight ?: 0

    }

    /**
     * Provided an **ImageView** and a base64 **data** String, it will try
     * to populate the view with the given **data**. If it can't be done, it
     * will use the provided **defaultImage** ID to populate the view from
     * the application resources folder.

     * @param imageView
     * *
     * @param data         Base64 encoded data for an Image.
     * *
     * @param defaultImage ID of a drawable or any other application resource that can be
     * *                     used to populate an ImageView
     * *
     * @author JoseMiguel
     */
    fun populateImageViewFromBase64Data(imageView: ImageView?,
                                        data: String,
                                        defaultImage: Int) {
        if (imageView == null) {
            return
        }

        if (TextUtils.isEmpty(data)) {
            imageView.setImageResource(defaultImage)
            return
        }

        val decodedByte = BitmapUtils.base64ToBitmap(data)
        if (decodedByte == null) {
            imageView.setImageResource(defaultImage)
            return
        }

        imageView.setImageBitmap(decodedByte)
    }

    /**
     * Provided a non-null **view** it will setup the view's click listener
     * as the provided **onClickListener**. If provided
     * **onClickListener** is null, it will cleanup the current view
     * listener.

     * @param view            The target [View]
     * *
     * @param onClickListener The [OnClickListener] to be added
     */
    fun setClickListener(view: View?,
                         onClickListener: OnClickListener) {
        if (view == null) {
            return
        }

        view.setOnClickListener(onClickListener)
    }

    /**
     * Provided a non-null **textView** it will set the text property to the
     * provided **value**. If the provided value is null or empty, it will
     * populate the text view with the provided **defaultValue**.

     * @param textView     The target text view to change the text property.
     * *
     * @param value        String to be used to populate the textView.
     * *
     * @param defaultValue The ID of the resource to use if the provided String happens
     * *                     to be null or empty.
     */
    fun setText(textView: TextView?, value: String, defaultValue: Int) {
        if (textView == null) {
            return
        }

        if (TextUtils.isEmpty(value)) {
            textView.setText(defaultValue)
            return
        }

        textView.text = value
    }

    /**
     * Provided a non-null **imageView** it will set the image source
     * property to the provided **resourceId**.

     * @param imageView  The target text view to change the image.
     * *
     * @param resourceId The ID of the resource to use to populate the imageView.
     */
    fun setImageResource(imageView: ImageView?, resourceId: Int) {
        if (imageView == null) {
            return
        }

        imageView.setImageResource(resourceId)
    }

    /**
     * Closes the Software keyboard attached to the view's [android.view.Window]

     * @param view The target [View]
     */
    fun hideKeyboard(view: View?) {
        val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * DISABLE THE PROVIDE VIEW

     * @param view The target [View]
     */
    fun disableView(view: View?) {
        view?.isEnabled = false
        view?.isClickable = false
    }

    /**
     * ENABLE THE VIEW

     * @param view The target [View]
     */
    fun enableView(view: View?) {
        view?.isEnabled = true
        view?.isClickable = true
    }

    /**
     * Provided a non-null [View] it will change the view's visibility by
     * the provided **visibility**.

     * @param view       The target [View]
     * *
     * @param visibility Target view visibility value
     */
    fun setVisibility(view: View?, visibility: Int) {
        if (view?.visibility == visibility) {
            return
        }

        view?.visibility = visibility
    }

    /**
     * @param textView
     * *
     * @param value
     */
    fun setText(
            textView: TextView?,
            value: String?
    ) {
        textView?.text = value ?: ""
    }

    /**
     * @param textView
     * *
     * @param value
     */
    fun setText(
            textView: TextView?,
            value: Spanned?
    ) {
        textView?.text = value ?: ""
    }

    /**
     * @param textView
     * *
     * @param value
     */
    fun setText(textView: TextView?, @StringRes value: Int) {
        if (value <= 0) {
            textView?.text = ""
        } else {
            textView?.setText(value)
        }
    }

    fun setVisible(
            visible: Int,
            view: View?
    ) {
        setVisible(View.VISIBLE == visible, view)
    }

    fun setVisible(
            visible: Boolean,
            view: View?
    ) {
        if (visible) {
            setVisibilityVisible(view)
        } else {
            setVisibilityGone(view)
        }
    }

    private fun setVisibilityVisible(view: View?) {
        setVisibility(view, View.VISIBLE)
    }

    private fun setVisibilityGone(view: View?) {
        setVisibility(view, View.GONE)
    }

    /**
     * @param view
     * *
     * @param listener
     */
    fun setListener(view: View?, listener: View.OnClickListener?) {
        if (listener == null) {
            return
        }

        view?.setOnClickListener(listener)
    }

    /**
     * SET MAX CHARACTERS IN THE TEXT VIEW

     * @param tv
     * *
     * @param length
     */
    fun setTextViewMaxCharacter(tv: TextView?, length: Int) {
        if (tv == null) {
            return
        }
        val fArray = arrayOfNulls<InputFilter>(1)
        fArray[0] = InputFilter.LengthFilter(length)
        tv.filters = fArray
    }

    /**
     * SET MAX LINES

     * @param tv
     * *
     * @param lines
     */
    fun setMaxLines(tv: TextView?, lines: Int) {
        tv?.maxLines = lines
    }

    /**
     * SHOW KEYBOARD

     * @param context
     * *
     * @param focusableView
     */
    fun showKeyboard(context: Context?, focusableView: View?) {
        focusableView?.requestFocus()
        val imm2 = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm2.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * @param context
     * *
     * @param focusableView
     * *
     * @author Jose Duque
     */
    fun hideKeyboard(context: Context?, focusableView: View?) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(focusableView?.windowToken, 0)
    }
}
