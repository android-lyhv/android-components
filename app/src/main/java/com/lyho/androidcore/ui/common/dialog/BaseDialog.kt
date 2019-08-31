package com.lyho.androidcore.ui.common.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.lyho.androidcore.R

/**
 * Base Dialog
 *
 * @author Ly Ho V.
 */
abstract class BaseDialog : DialogFragment() {
    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(getWithDialog(), getHeightDialog())
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            getDimAmount()?.let {
                setDimAmount(it)
            }
        }
        dialog?.setCanceledOnTouchOutside(isCanTouchOutside())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getWindowAnimation()?.let {
            dialog?.window?.attributes?.windowAnimations = it
        }
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

    open fun getWithDialog(): Int {
        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        return size.x - getPixelMarginDialog()
    }

    open fun getDimAmount(): Float? = null
    open fun getPixelMarginDialog() = resources.getDimensionPixelSize(R.dimen._30sdp)
    open fun getHeightDialog(): Int = ViewGroup.LayoutParams.WRAP_CONTENT
    open fun getWindowAnimation(): Int? = null
    open fun isCanTouchOutside(): Boolean = true
}
