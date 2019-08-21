package com.lyho.androidcore.ui.common

import android.view.View

/**
 * This interface is used to avoid clicking many times.
 *
 */
abstract class BaseOnClickListener : View.OnClickListener {

    abstract fun onItemClick(v: View)

    override fun onClick(v: View) {
        if (isBlockingClick) {
            return
        }
        onItemClick(v)
    }

    companion object {
        private const val MIN_CLICK_INTERVAL = 500

        private var sLastClickTime: Long = 0

        val isBlockingClick: Boolean
            get() = isBlockingClick(MIN_CLICK_INTERVAL.toLong())

        private fun isBlockingClick(minClickInterval: Long): Boolean {
            val isBlocking: Boolean
            val currentTime = System.currentTimeMillis()
            isBlocking = Math.abs(currentTime - sLastClickTime) < minClickInterval
            if (!isBlocking) {
                sLastClickTime = currentTime
            }
            return isBlocking
        }
    }
}
