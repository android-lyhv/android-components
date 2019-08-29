package com.lyho.androidcore.ui.common

import android.content.Context
import androidx.fragment.app.Fragment


/**
 * Created by Ly Ho V. on April 04, 2018
 */
abstract class BaseMainFragment : Fragment() {
    var mRootMainListener: BaseMainListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mRootMainListener = context as BaseMainListener
        } catch (ex: ClassCastException) {
            throw RuntimeException("The activity must implement BaseMainListener")
        }
    }

    fun getFragmentTag(): String {
        return this.javaClass.name
    }
}