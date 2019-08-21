import android.graphics.Point
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.ViewGroup

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

    open fun getWithDialog(): Int {
        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        return size.x
    }

    open fun getHeightDialog(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    open fun getWindowAnimation(): Int? {
        return null
    }
}
