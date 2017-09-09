package com.energolabs.evergo.ui.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.ViewsController
import com.energolabs.evergo.ui.activities.BaseActivity
import com.energolabs.evergo.utils.ToastUtil

/**
 * Created by JoseMiguel on 6/8/2015.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
abstract class BaseFragment : Fragment() {

    protected var parentView: View? = null
    protected var baseActivity: BaseActivity? = null
    protected var inflater: LayoutInflater? = null
    protected var container: ViewGroup? = null
    protected var rootView: ViewGroup? = null
    protected var toolbar: Toolbar? = null
    protected var actionBar: ActionBar? = null
    protected var waitDialog: android.app.AlertDialog? = null
    protected var tv_text: TextView? = null

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected fun showNoNetworkError() {
        hideWaitDialog()
        if (activity == null) {
            return
        }

        ToastUtil.showToastShort(
                activity,
                R.string.str_no_connected_to_internet
        )
    }

    private fun initAndShowWaitDialog() {
        val waitDialog = ProgressDialog(context)
        waitDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        waitDialog.setCancelable(false)
        waitDialog.show()
    }

    protected fun showWaitDialog() {
        if (waitDialog == null) {
            initAndShowWaitDialog()
        } else {
            waitDialog?.show()
        }
    }

    fun showWaitDialog(@StringRes resId: Int) {
        if (waitDialog == null) {
            initAndShowWaitDialog()
        } else {
            waitDialog?.show()
        }
        tv_text?.setText(resId)
    }

    protected fun hideWaitDialog() {
        if (waitDialog == null) {
            return
        }

        if (!(waitDialog?.isShowing ?: false)) {
            return
        }
        waitDialog?.dismiss()
    }

    fun getStringSafely(@StringRes resId: Int): String {
        try {
            return getString(resId)
        } catch (ignored: Exception) {
            return ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.baseActivity = activity as BaseActivity
        this.toolbar = baseActivity?.toolbar
        this.actionBar = baseActivity?.supportActionBar
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (this.rootView != null) {
            return this.rootView
        }

        this.inflater = inflater
        this.container = container
        val rootView = inflater.inflate(
                layoutId,
                container,
                false
        ) as ViewGroup

        this.rootView = rootView
        this.parentView = rootView

        findViews(rootView)
        setListeners()

        return rootView
    }

    protected abstract fun findViews(view: View)

    @CallSuper
    protected open fun setListeners() {
    }

    protected fun setTitle(title: String?) {
        if (TextUtils.isEmpty(title)) {
            return
        }

        activity?.title = title
        toolbar?.title = title
        actionBar?.title = title
    }

    protected fun setTitle(@StringRes resId: Int) {
        activity?.setTitle(resId)
        toolbar?.setTitle(resId)
        actionBar?.setTitle(resId)
    }

    /**
     * SHOW SIMPLE DIALOG BOX WITH PROVIDED MESSAGE AND SINGLE BUTTON TO HIDE THE DIALOG

     * @param context                of the application
     * *
     * @param message                content for the dialog
     * *
     * @param positiveButtonListener action to perform on dismiss
     */
    @JvmOverloads protected fun showMessageDialog(
            context: Context,
            message: String,
            positiveButtonListener: DialogInterface.OnClickListener? = null
    ) {
        showMessageDialog(
                context,
                message,
                null,
                positiveButtonListener
        )
    }

    /**
     * SHOW SIMPLE DIALOG BOX WITH PROVIDED MESSAGE AND SINGLE BUTTON TO HIDE THE DIALOG

     * @param context                of the application
     * *
     * @param message                content for the dialog
     * *
     * @param positiveButtonListener action to perform on dismiss
     */
    protected fun showMessageDialog(
            context: Context?,
            message: String,
            title: String?,
            positiveButtonListener: DialogInterface.OnClickListener?
    ) {
        if (context == null) {
            return
        }

        val builder = AlertDialog.Builder(
                context
        )

        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message)
        }

        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title)
        }

        builder.setPositiveButton(
                android.R.string.ok,
                positiveButtonListener
        )
        builder.show()
    }

    /**
     * SHOW SIMPLE DIALOG BOX WITH PROVIDED MESSAGE AND SINGLE BUTTON TO HIDE THE DIALOG

     * @param context                of the application
     * *
     * @param message                content for the dialog
     * *
     * @param positiveButtonListener action to perform on dismiss
     */
    protected fun showMessageDialog(
            context: Context?,
            @StringRes message: Int,
            @StringRes title: Int,
            positiveButtonListener: DialogInterface.OnClickListener?
    ) {
        if (context == null) {
            return
        }

        val builder = createMessageDialog(
                context,
                message,
                title
        )

        builder?.setPositiveButton(
                android.R.string.ok,
                positiveButtonListener
        )
        builder?.show()
    }


    /**
     * SHOW SIMPLE DIALOG BOX WITH PROVIDED MESSAGE AND SINGLE BUTTON TO HIDE THE DIALOG

     * @param context Activity context
     * *
     * @param message Resource ID pointing to the string to be used as message of the dialog.
     * *
     * @param title   Resource ID pointing to the string to be used as title of the dialog.
     */
    protected fun createMessageDialog(
            context: Context?,
            @StringRes message: Int,
            @StringRes title: Int
    ): AlertDialog.Builder? {
        if (context == null) {
            return null
        }

        val builder = AlertDialog.Builder(
                context
        )

        if (message > 0) {
            builder.setMessage(message)
        }

        if (title > 0) {
            builder.setTitle(title)
        }

        return builder
    }

    protected abstract fun disableViews()

    protected abstract fun enableViews()

    fun updateUI() {
        rootView?.removeAllViews()
        rootView = null
        container?.removeAllViews()
        onCreateView(
                inflater ?: return,
                container,
                null
        )
        ViewsController.showView(rootView)
        container?.addView(rootView)
    }

    open fun onBackPressed(): Boolean {
        ViewsController.hideKeyboard(
                activity,
                activity?.currentFocus
        )
        return false
    }

}
/**
 * SHOW SIMPLE DIALOG BOX WITH PROVIDED MESASGE AND SINGLE BUTTON TO HIDE THE DIALOG

 * @param context of the application
 * *
 * @param message content for the dialog
 */
