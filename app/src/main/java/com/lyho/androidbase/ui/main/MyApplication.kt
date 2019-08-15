package com.lyho.androidbase.ui.main

import android.app.Application
import com.lyho.androidbase.model.network.ApiClient

/**
 * Created by Ly Ho V. on 25 November 2017
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.getInstance()?.init(this)
    }
}
