package com.lampurema.androidvpn.ui.main

import android.app.Application
import com.lampurema.androidvpn.model.remote.ApiClient

/**
 * Created by Ly Ho V. on 25 November 2017
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.getInstance.init(this)
    }
}
