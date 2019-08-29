package com.lyho.androidcore.ui.common.recyclerview

import android.view.View
import kotlin.math.abs

/**
 * This interface is used to avoid clicking many times.
 *
 */
interface OnBlockViewClickListener : View.OnClickListener {

    override fun onClick(v: View) {
        if (isBlockingClick) {
            return
        }
    }

    companion object {
        private const val MIN_CLICK_INTERVAL = 500

        private var mLastClickTime: Long = 0

        val isBlockingClick: Boolean
            get() = isBlockingClick(
                MIN_CLICK_INTERVAL.toLong()
            )

        private fun isBlockingClick(minClickInterval: Long): Boolean {
            val currentTime = System.currentTimeMillis()
            val isBlocking = abs(currentTime - mLastClickTime) < minClickInterval
            if (!isBlocking) {
                mLastClickTime = currentTime
            }
            return isBlocking
        }
    }
}
