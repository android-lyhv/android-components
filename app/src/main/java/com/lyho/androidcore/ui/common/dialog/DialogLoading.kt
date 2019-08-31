package com.lyho.androidcore.ui.common.dialog

import android.content.Context
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import com.lyho.androidcore.R

/**
 * Created by lyhv on August 31, 2019
 * Copyright @ est-rouge. All rights reserved
 */
class DialogLoading(context: Context) : AlertDialog.Builder(context) {
    init {
        setOnKeyListener { _, keyCode, event -> keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP }
        setView(getViewId())
        setCancelable(false)
    }

    open fun getViewId(): Int {
        return R.layout.view_loading
    }

    companion object {
        fun newInstance(context: Context): AlertDialog {
            return DialogLoading(context).create()
        }
    }
}
