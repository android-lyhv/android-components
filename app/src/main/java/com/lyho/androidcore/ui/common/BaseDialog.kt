package uk.co.snaprevise.snaprevise.ui.common

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
        dialog?.window?.setLayout(getWithDialog(), getHeightDialog())
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getWindowAnimation()?.let {
            dialog?.window?.attributes?.windowAnimations = it
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setCanceledOnTouchOutside(isCanTouchOutside())
        return dialog
    }

    open fun getWithDialog(): Int {
        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        return size.x - getPixelMarginDialog()
    }

    open fun getPixelMarginDialog(): Int {
        return resources.getDimensionPixelSize(R.dimen._30sdp)
    }

    open fun getHeightDialog(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    open fun getWindowAnimation(): Int? {
        return null
    }

    open fun isCanTouchOutside(): Boolean {
        return true
    }
}
