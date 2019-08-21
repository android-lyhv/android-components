package com.lyho.androidcore.ui.main

import android.app.Application
import com.lyho.androidcore.model.network.ApplicationConfig

/**
 * Created by Ly Ho V. on 25 November 2017
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationConfig.injectApplication(this)
    }
}
