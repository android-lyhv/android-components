package com.lyho.androidcore.ui.common

import android.content.Context


/**
 * Created by Ly Ho V. on April 04, 2018
 */
abstract class BaseMainFragment : BaseFragment() {
    var mRootMainListener: BaseMainListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mRootMainListener = context as BaseMainListener
        } catch (ex: ClassCastException) {
            throw RuntimeException("The activity must implement BaseMainListener")
        }
    }
}