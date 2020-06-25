package com.lyhv.component.common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import com.lyhv.component.R


/**
 * Created by lyhv on August 31, 2019
 * Copyright @ est-rouge. All rights reserved
 */
class DialogLoading(private val mContext: Context) : BaseDialog() {
    override fun getCustomDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(mContext)
        builder.setOnKeyListener { _, keyCode, event -> keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP }
        builder.setCancelable(false)
        val dialogView = activity?.layoutInflater?.inflate(getLayoutId(), null, false)
        dialogView?.let {
            builder.setView(it)
        }
        return builder.create()
    }

    override fun isCanTouchOutside(): Boolean {
        return false
    }

    fun getLayoutId(): Int {
        return R.layout.view_loading
    }

    companion object {
        fun newInstance(context: Context): DialogLoading {
            return DialogLoading(context)
        }
    }
}
