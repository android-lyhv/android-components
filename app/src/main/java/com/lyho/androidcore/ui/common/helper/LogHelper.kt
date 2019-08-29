package com.lyho.androidcore.ui.common.helper

import android.util.Log
import com.lyho.androidcore.BuildConfig

/**
 * Created by lyhv on August 29, 2019
 * Copyright @ est-rouge. All rights reserved
 */
object LogHelper {
    const val TAG_COROUTINES = "Couroutines"

    init {
        System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")
    }

    fun logCouroutine(message: String) {
        Log.d(TAG_COROUTINES + " ${Thread.currentThread().name}", message)
    }
}
