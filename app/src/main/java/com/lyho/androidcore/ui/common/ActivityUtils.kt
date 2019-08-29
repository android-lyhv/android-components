import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.lyho.androidcore.R
import com.lyho.androidcore.ui.common.BaseMainFragment


object ActivityUtils {
    fun replaceFragment(
        fragmentManager: FragmentManager,
        mainFragment: BaseMainFragment, @IdRes containerId: Int,
        typeAnimation: Int = TYPE_1
    ) {
        val transaction = fragmentManager.beginTransaction()
        setCustomAnimations(typeAnimation, transaction)
        transaction.addToBackStack(mainFragment.getFragmentTag())
        transaction.replace(containerId, mainFragment, mainFragment.getFragmentTag())
        transaction.commitAllowingStateLoss()
    }

    fun replaceFragmentWithoutStack(
        fragmentManager: FragmentManager,
        mainFragment: BaseMainFragment, @IdRes containerId: Int,
        typeAnimation: Int = TYPE_1
    ) {
        val transaction = fragmentManager.beginTransaction()
        setCustomAnimations(typeAnimation, transaction)
        transaction.replace(containerId, mainFragment, mainFragment.getFragmentTag())
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