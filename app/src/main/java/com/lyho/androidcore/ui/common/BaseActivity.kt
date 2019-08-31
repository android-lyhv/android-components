package com.lyho.androidcore.ui.common

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Created by Ly Ho V. on April 04, 2018
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}