package com.lyho.androidcore.ui.common

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
        super.onDestroy()
        cancel()
    }
}