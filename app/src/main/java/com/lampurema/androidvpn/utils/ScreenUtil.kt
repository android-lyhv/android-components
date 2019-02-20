import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Utilities for getting screen size or convert "dp" to "px".
 */
object ScreenUtil {
    /**
     * This method is used to get strokeWidth of screen.
     *
     * @param context context
     * @return return strokeWidth screen in pixel
     */
    fun getHeightScreen(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display?.getRealMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    /**
     * This method is used to get width of screen.
     *
     * @param context context
     * @return return width of screen in pixel
     */
    fun getWidthScreen(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display?.getRealMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun dpToPixels(context: Context, dp: Int): Float {
        return dp * context.resources.displayMetrics.density
    }
}
