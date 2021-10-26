package com.lyhv.mvvm.common.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment() {
    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(getWithDialog(), getHeightDialog())
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            getDimAmount()?.let {
                setDimAmount(it)
            }
        }
        dialog?.setCanceledOnTouchOutside(cancelOutside())
        isCancelable = cancelable()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.attributes?.windowAnimations = getStyleWindowAnimation()
        super.onViewCreated(view, savedInstanceState)
    }

    final override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = getCustomDialog(savedInstanceState)
        dialog.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
        }
        return dialog
    }

    open fun getCustomDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    open fun getWithDialog(): Int = ViewGroup.LayoutParams.WRAP_CONTENT
    open fun getHeightDialog(): Int = ViewGroup.LayoutParams.WRAP_CONTENT
    open fun getDimAmount(): Float? = null
    open fun getStyleWindowAnimation(): Int = 0
    open fun cancelOutside(): Boolean = true
    open fun cancelable(): Boolean = true
}
