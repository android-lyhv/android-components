import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.lampurema.androidvpn.R


object ActivityUtils {
    fun replaceFragment(
        fragmentManager: FragmentManager,
        fragment: BaseFragment, @IdRes containerId: Int,
        typeAnimation: Int = TYPE_1
    ) {
        val transaction = fragmentManager.beginTransaction()
        setCustomAnimations(typeAnimation, transaction)
        transaction.addToBackStack(fragment.getFragmentTag())
        transaction.replace(containerId, fragment, fragment.getFragmentTag())
        transaction.commitAllowingStateLoss()
    }

    fun replaceFragmentWithoutStack(
        fragmentManager: FragmentManager,
        fragment: BaseFragment, @IdRes containerId: Int,
        typeAnimation: Int = TYPE_1
    ) {
        val transaction = fragmentManager.beginTransaction()
        setCustomAnimations(typeAnimation, transaction)
        transaction.replace(containerId, fragment, fragment.getFragmentTag())
        transaction.commitAllowingStateLoss()
    }

    /**
     * Fragment enter slide 100% to 0%
     * Fragment exit slide 0% to -100%
     */
    const val TYPE_1 = 1
    /**
     * Fragment enter slide 0% to -100%
     * Fragment exit slide 100% to 0%
     */
    const val TYPE_2 = 2

    const val TYPE_NONE = 0


    private fun setCustomAnimations(typeAnimation: Int, transaction: FragmentTransaction) {
        when (typeAnimation) {
            TYPE_1 -> transaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            TYPE_2 -> transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}