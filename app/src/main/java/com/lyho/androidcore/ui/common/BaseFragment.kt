package com.lyho.androidcore.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Created by lyhv on August 31, 2019
 * Copyright @ est-rouge. All rights reserved
 */
abstract class BaseFragment : Fragment(), CoroutineScope by MainScope() {
    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    /**
     * @return The layout id from xml
     */
    abstract fun getLayoutId(): Int
}