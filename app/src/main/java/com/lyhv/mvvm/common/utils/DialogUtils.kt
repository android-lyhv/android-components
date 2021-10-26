package com.lyhv.mvvm.common.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lyhv.mvvm.R

object DialogUtils {
    fun createErrorDialog(context: Context, string: String): AlertDialog {
        val builder = MaterialAlertDialogBuilder(context, R.style.CommonDialogStyle)
            .setTitle(string)
            .setNegativeButton(R.string.negative_button_title, null)
        return builder.create()
    }
}