package com.energolabs.evergo.ui.dialogs

import android.app.Activity
import android.content.DialogInterface
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText

import com.energolabs.evergo.R

/**
 * Created by Jose Duque on 12/23/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class TextInputDialog {

    private var input: EditText? = null

    fun showDialog(
            activity: Activity?,
            root: ViewGroup,
            @StringRes title: Int,
            currentValue: String,
            @StringRes hint: Int,
            positiveButtonListener: DialogInterface.OnClickListener
    ) {
        activity ?: return
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        // I'm using fragment here so I'm using getView() to provide ViewGroup
        // but you can provide here any other instance of ViewGroup from your Fragment / Activity
        val viewInflated = LayoutInflater
                .from(activity)
                .inflate(
                        R.layout.dialog_text_input,
                        root,
                        false
                )
        // Set up the input
        input = viewInflated.findViewById(R.id.input) as EditText
        input?.setText(currentValue)
        input?.setHint(hint)
        input?.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated)

        // Set up the buttons
        builder.setPositiveButton(
                android.R.string.ok,
                positiveButtonListener)
        builder.setNegativeButton(
                android.R.string.cancel
        ) { dialog, which -> dialog.cancel() }

        builder.show()
    }

    fun getInput(): String {
        return input?.text.toString()
    }
}
