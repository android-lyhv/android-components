import android.content.Context
import android.support.v4.app.Fragment
import com.lampurema.androidvpn.ui.common.BaseMainListener


/**
 * Created by Ly Ho V. on April 04, 2018
 */
abstract class BaseFragment : Fragment() {
    var mRootMainListener: BaseMainListener? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mRootMainListener = context as BaseMainListener
        } catch (ex: ClassCastException) {
            // No-op
        }
    }

    fun getFragmentTag(): String {
        return this.javaClass.name
    }
}